package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.UserResponseDTO;
import br.edu.gti.gestao_incidentes.entities.user.User;

public class UserMapper {

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

    public static User toEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) return null;
        User user = new User();

        user.setName(userRequestDTO.name());
        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setProfile(userRequestDTO.profile());

        return user;
    }

    public static void applyChanges(UserRequestDTO userRequestDTO, User user) {

        if (userRequestDTO.name() != null) user.setName(userRequestDTO.name());
        if (userRequestDTO.username() != null) user.setUsername(userRequestDTO.username());
        if (userRequestDTO.email() != null) user.setEmail(userRequestDTO.email());
        if (userRequestDTO.profile() != null) user.setProfile(userRequestDTO.profile());
    }
}
