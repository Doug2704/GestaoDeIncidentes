package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.enums.Level;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.*;

import java.util.List;

public record PlanRequestDTO(

        @NotBlank(message = "Título é obrigatório", groups = OnCreate.class)
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Utilize apenas letras no título")
        @Size(min = 5, message = "Título precisa ter, pelo menos, {min} caracteres.")
        String title,

        @NotEmpty(message = "Pelo menos uma etapa deve ser informada", groups = OnCreate.class)
        List<Step> steps,

        @NotNull(message = "Informe a área responsável", groups = OnCreate.class)
        Area responsibleArea,

        @NotEmpty(message = "Pelo menos uma área afetada deve ser informada", groups = OnCreate.class)
        List<Area> affectedAreas,

        @NotBlank(message = "Descreva o incidente", groups = OnCreate.class)
        @Size(min = 10, message = "Descrição do incidente precisa ter, pelo menos, {min} caracteres.")
        String incidentDescription,

        @NotNull(message = "Defina um usuário", groups = OnCreate.class)
        UserRequestDTO userRequestDTO,

        @NotNull(message = "Defina o nível de urgência do incidente", groups = OnCreate.class)
        Level urgencyLevel,

        @NotNull(message = "Defina o nível de impacto do incidente", groups = OnCreate.class)
        Level impactLevel
) {
}