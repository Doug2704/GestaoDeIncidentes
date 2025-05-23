package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.enums.Status;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StepRequestDTO(

        @NotNull
        Long planId,

        @NotBlank(message = "Título é obrigatório.", groups = OnCreate.class)
        String title,

        @NotEmpty(message = "Pelo menos uma ação deve ser informada", groups = OnCreate.class)
        List<ActionRequestDTO> actionRequestDTOS,

        @NotNull(message = "ID da área responsável é obrigatório", groups = OnCreate.class)
        Long responsibleAreaId,

        Status status
) {
}
