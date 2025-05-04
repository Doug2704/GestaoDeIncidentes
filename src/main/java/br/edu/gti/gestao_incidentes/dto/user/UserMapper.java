package br.edu.gti.gestao_incidentes.dto.user;


import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.enums.Profile;

public class UserMapper {

    public static UserResponseDTO toDto(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getActuationArea(),
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

}
