package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDTO(
        @NotBlank(message = "Ação é obrigatória.", groups = OnCreate.class)
        String action,

        @NotNull(message = "Defina a qual etapa pertence essa tarefa.", groups = OnCreate.class)
        Step step,

        boolean isDone
) {
}
