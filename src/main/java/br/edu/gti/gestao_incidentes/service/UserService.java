package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.UserMapper;
import br.edu.gti.gestao_incidentes.dto.requests.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.UserResponseDTO;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User create(Long actuaAtionAreaId, @Validated(OnCreate.class) UserRequestDTO userRequestDTO) {
        try {
            User user = UserMapper.toEntity(userRequestDTO, actuaAtionAreaId);
            user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<UserResponseDTO> findByAreaId(Long actuationAreaId) {
        return userRepository.findByActuationArea_Id(actuationAreaId).stream().map(UserMapper::toDto).toList();
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum usuário com id: " + id));
        return UserMapper.toDto(user);
    }

    public User update(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("nenhum usuário com id: " + id));
        try {
            UserMapper.applyChanges(userRequestDTO, user);
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("nenhum usuário com id: " + id);
        }
        userRepository.deleteById(id);
    }

}
