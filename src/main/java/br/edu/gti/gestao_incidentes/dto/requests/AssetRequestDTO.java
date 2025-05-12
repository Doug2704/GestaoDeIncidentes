package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AssetRequestDTO(

        @NotBlank(message = "Nome do ativo é obrigatório", groups = OnCreate.class)
        @Size(min = 2, message = "Nome do ativo precisa ter, pelo menos, {min} caracteres.")
        String name,

        @NotNull(message = "Área responsável é obrigatória", groups = OnCreate.class)
        Area responsibleArea
) {
}
