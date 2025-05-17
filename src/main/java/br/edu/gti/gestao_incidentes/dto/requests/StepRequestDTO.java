package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.entities.Task;
import br.edu.gti.gestao_incidentes.enums.Status;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StepRequestDTO(
        @NotBlank(message = "Título é obrigatório.", groups = OnCreate.class)
        String title,

        @NotEmpty(message = "Pelo menos uma tarefa deve ser informada", groups = OnCreate.class)
        List<Task> tasks,

        @NotNull(message = "ID da área responsável é obrigatório", groups = OnCreate.class)
        Long responsibleAreaId,

        Status status
) {
}
