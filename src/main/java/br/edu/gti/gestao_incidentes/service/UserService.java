package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.UserMapper;
import br.edu.gti.gestao_incidentes.dto.requests.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.UserResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import br.edu.gti.gestao_incidentes.validation.OnUpdate;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
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
    private final AreaRepository areaRepository;
    private final UserMapper userMapper;
    private final AuditLogService auditLogService;

    public UserResponseDTO create(Long actuationAreaId, @Validated(OnCreate.class) UserRequestDTO userRequestDTO) {
        try {
            User user = userMapper.toEntity(userRequestDTO);
            Area actuationArea = areaRepository.findById(actuationAreaId)
                    .orElseThrow(() -> new NoRegisterException(actuationAreaId));
            user.setActuationArea(actuationArea);
            user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
            return userMapper.toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<UserResponseDTO> findByAreaId(Long actuationAreaId) {
        if (!areaRepository.existsById(actuationAreaId)) {
            throw new NoRegisterException(actuationAreaId);
        }
        return userRepository.findByActuationArea_Id(actuationAreaId).stream().map(userMapper::toDto).toList();
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        return userMapper.toDto(user);
    }

    //TODO implementar alteração de área de atuação
    public UserResponseDTO update(Long id, @Validated(OnUpdate.class) UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoRegisterException(id));
        try {
            if (userRequestDTO.password() != null) user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
            userMapper.applyChanges(userRequestDTO, user);

            return userMapper.toDto(userRepository.save(user));

        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoRegisterException(id);
        }
        userRepository.deleteById(id);
    }
}
