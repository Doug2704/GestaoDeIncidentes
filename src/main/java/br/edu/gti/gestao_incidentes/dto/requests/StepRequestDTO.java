package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
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

        @NotNull(message = "É necessário associar a um plano de ação", groups = OnCreate.class)
        ActionPlan actionPlan,

        @NotEmpty(message = "Pelo menos uma tarefa deve ser informada", groups = OnCreate.class)
        List<Task> tasks,

        @NotNull(message = "Informe a área responsável", groups = OnCreate.class)
        Area responsibleArea,

        Status status
) {
}
