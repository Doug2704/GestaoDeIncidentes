package br.edu.gti.gestao_incidentes.service;

import br.com.candido.conexaoestagios.dto.students.StudentMapper;
import br.com.candido.conexaoestagios.dto.students.StudentRequestDTO;
import br.com.candido.conexaoestagios.dto.students.StudentResponseDTO;
import br.com.candido.conexaoestagios.dto.students.StudentUpdateDTO;
import br.com.candido.conexaoestagios.entities.Student;
import br.com.candido.conexaoestagios.exceptions.EmailAlreadyExistsException;
import br.com.candido.conexaoestagios.exceptions.NoStudentException;
import br.com.candido.conexaoestagios.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentResponseDTO create(@Valid StudentRequestDTO studentRequestDTO) {

        if (studentRepository.findByEmail(studentRequestDTO.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        Student student = StudentMapper.toEntity(studentRequestDTO);
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime localDateTime = ZonedDateTime.now(zoneId).toLocalDateTime();
        student.setRegistrationDate(localDateTime);

        return StudentMapper.toDto(studentRepository.save(student));
    }

    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll().stream().map(StudentMapper::toDto).toList();
    }

    public StudentResponseDTO findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NoStudentException(id));
        return StudentMapper.toDto(student);
    }

    //TODO: alterar lógica para garantir que o email pertence a outro estudante antes de lançar a exceção
    public StudentResponseDTO update(Long id, StudentUpdateDTO studentUpdateDTO) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NoStudentException(id));
        if (studentRepository.findByEmail(studentUpdateDTO.email()).isPresent())  throw new EmailAlreadyExistsException();

        if (studentUpdateDTO.name() != null) student.setName(studentUpdateDTO.name());
        if (studentUpdateDTO.email() != null) student.setEmail(studentUpdateDTO.email());
        if (studentUpdateDTO.course() != null) student.setCourse(studentUpdateDTO.course());
        if (studentUpdateDTO.institution() != null) student.setInstitution(studentUpdateDTO.institution());
        if (studentUpdateDTO.phoneNumber() != null) student.setPhoneNumber(studentUpdateDTO.phoneNumber());
        if (studentUpdateDTO.city() != null) student.setCity(studentUpdateDTO.city());
        if (studentUpdateDTO.state() != null) student.setState(studentUpdateDTO.state());

        return StudentMapper.toDto(studentRepository.save(student));
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NoStudentException(id);
        }
        Student student = studentRepository.getReferenceById(id);
        studentRepository.delete(student);
    }
}