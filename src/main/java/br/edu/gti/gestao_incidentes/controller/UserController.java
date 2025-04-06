package br.edu.gti.gestao_incidentes.controller;

import br.com.candido.conexaoestagios.dto.students.StudentRequestDTO;
import br.com.candido.conexaoestagios.dto.students.StudentResponseDTO;
import br.com.candido.conexaoestagios.dto.students.StudentUpdateDTO;
import br.com.candido.conexaoestagios.exceptions.EmailAlreadyExistsException;
import br.com.candido.conexaoestagios.exceptions.NoStudentException;
import br.com.candido.conexaoestagios.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estudantes")
@Tag(name = "Estudantes", description = "Gerenciamento de estudantes")
public class UserController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Criar Estudante", description = "Cria e salva um estudante no banco de dados.")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            StudentResponseDTO createdStudent = studentService.create(studentRequestDTO);
            URI local = URI.create("/" + createdStudent.id());
            return ResponseEntity.created(local).body(createdStudent);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Buscar Estudantes", description = "Retorna todos os estudantes do banco ou, se não houver nenhum, retorna uma lista vazia.")
    public ResponseEntity<List<StudentResponseDTO>> findAllStudents() {

        List<StudentResponseDTO> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Estudante", description = "Retorna um estudante pelo ID fornecido.")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            StudentResponseDTO foundStudent = studentService.findById(id);
            return ResponseEntity.ok(foundStudent);
        } catch (NoStudentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Estudante", description = "Altera as informações de um estudante caso exista no banco de dados.")
    public ResponseEntity<?> atualizarEstudante(@Valid @RequestBody StudentUpdateDTO studentUpdateDTO, @PathVariable long id) {
        try {
            StudentResponseDTO updatedStudent = studentService.update(id, studentUpdateDTO);
            return ResponseEntity.ok(updatedStudent);
        } catch (NoStudentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apagar Estudante", description = "Apaga o estudante ou, se não existir no banco, retorna uma mensagem de Estudante Inexistente.")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoStudentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
