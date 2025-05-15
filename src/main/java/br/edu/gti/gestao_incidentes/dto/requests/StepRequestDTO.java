package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.enums.Status;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StepRequestDTO(
        @NotBlank(message = "Título é obrigatório.", groups = OnCreate.class)
        String title,

        @NotNull(message = "É necessário associar a um plano de ação", groups = OnCreate.class)
        ActionPlan actionPlan,

        @NotBlank(message = "Defina uma ação para essa etapa", groups = OnCreate.class)
        @Size(min = 10, message = "Ação precisa ter, pelo menos, {min} caracteres.")
        String action,

        @NotNull(message = "Informe a área responsável", groups = OnCreate.class)
        Area responsibleArea,

        Status status
) {
}
