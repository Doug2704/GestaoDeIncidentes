package br.edu.gti.gestao_incidentes.dto.responses;

import br.edu.gti.gestao_incidentes.enums.Profile;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String username,
        String email,
        String actuationArea,
        Profile profile,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime registrationDate
) {
}
