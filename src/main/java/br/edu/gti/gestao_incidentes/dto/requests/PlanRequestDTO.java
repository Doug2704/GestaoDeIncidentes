package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.enums.Level;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PlanRequestDTO(

        @NotBlank(message = "Título é obrigatório", groups = OnCreate.class)
        @Size(min = 5, message = "Título precisa ter, pelo menos, {min} caracteres.")
        String title,

        @NotEmpty(message = "Pelo menos uma etapa deve ser informada", groups = OnCreate.class)
        List<StepRequestDTO> stepRequestDTOs,

        @NotEmpty(message = "Pelo menos uma área afetada deve ser informada", groups = OnCreate.class)
        List<Long> affectedAreasIds,

        @NotBlank(message = "Descreva o incidente", groups = OnCreate.class)
        @Size(min = 10, message = "Descrição do incidente precisa ter, pelo menos, {min} caracteres.")
        String incidentDescription,

        @NotNull(message = "Defina o nível de urgência do incidente", groups = OnCreate.class)
        Level urgencyLevel,

        @NotNull(message = "Defina o nível de impacto do incidente", groups = OnCreate.class)
        Level impactLevel,

        @NotNull(message = "Defina uma duração máxima sugerida para a execução desse plano de ação.", groups = OnCreate.class)
        Long planMaxDuration
) {
}