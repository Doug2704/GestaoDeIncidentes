package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.user.UserMapper;
import br.edu.gti.gestao_incidentes.dto.user.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.user.UserResponseDTO;
import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User create(UserRequestDTO userRequestDTO) {
        try {
            User user = UserMapper.toEntity(userRequestDTO);
            user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum usuário com id: " + id));
        return UserMapper.toDto(user);
    }

    //TODO criar método para alterar senha
    public User update(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("nenhum usuário com id: " + id));
        try {
            if (userRequestDTO.name() != null) user.setName(userRequestDTO.name());
            if (userRequestDTO.username() != null) user.setUsername(userRequestDTO.username());
            if (userRequestDTO.email() != null) user.setEmail(userRequestDTO.email());
            if (userRequestDTO.actuationArea() != null) user.setActuationArea(userRequestDTO.actuationArea());
            if (userRequestDTO.profile() != null) user.setProfile(userRequestDTO.profile()
            );
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
