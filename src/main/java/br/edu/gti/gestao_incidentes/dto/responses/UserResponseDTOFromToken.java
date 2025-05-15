package br.edu.gti.gestao_incidentes.dto.responses;

import br.edu.gti.gestao_incidentes.enums.Profile;

public record UserResponseDTOFromToken(
        Long id,
        String username,
        Profile profile,
        String profileDescription
) {
}
