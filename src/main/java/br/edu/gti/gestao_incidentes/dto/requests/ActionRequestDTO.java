package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;

public record ActionRequestDTO(
        @NotBlank(message = "Título é obrigatório.", groups = OnCreate.class)
        String title
) {
}
