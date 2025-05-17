package br.edu.gti.gestao_incidentes.dto.mappers;


import br.edu.gti.gestao_incidentes.dto.requests.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.UserResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    private static PasswordEncoder passwordEncoder;
    private static AreaRepository areaRepository;

    public static UserResponseDTO toDto(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getActuationArea().getName(),
                user.getProfile(),
                user.getRegistrationDate()
        );
    }

    public static User toEntity(UserRequestDTO userRequestDTO, Long actuationAreaId) {
        if (userRequestDTO == null) return null;
        User user = new User();
        Area actuationArea = areaRepository.findById(actuationAreaId)
                .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));

        user.setName(userRequestDTO.name());
        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setActuationArea(actuationArea);
        user.setProfile(userRequestDTO.profile());

        return user;
    }

    public static void applyChanges(UserRequestDTO userRequestDTO, User user) {
        if (userRequestDTO.actuationAreaId() != null) {
            Area actuationArea = areaRepository.findById(userRequestDTO.actuationAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));
            user.setActuationArea(actuationArea);
        }

        if (userRequestDTO.name() != null) user.setName(userRequestDTO.name());
        if (userRequestDTO.username() != null) user.setUsername(userRequestDTO.username());
        if (userRequestDTO.email() != null) user.setEmail(userRequestDTO.email());
        if (userRequestDTO.profile() != null) user.setProfile(userRequestDTO.profile());
        if (userRequestDTO.password() != null) user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
    }
}
