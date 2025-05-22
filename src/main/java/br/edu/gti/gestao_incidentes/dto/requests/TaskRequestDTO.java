package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;

//TODO verificar se há método done em task service
public record TaskRequestDTO(
        @NotBlank(message = "Ação é obrigatória.", groups = OnCreate.class)
        String action

) {
}
