package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AreaRequestDTO(

        @NotBlank(message = "Nome da área é obrigatório", groups = OnCreate.class)
        @Size(min = 2, message = "Nome da área precisa ter, pelo menos, {min} caracteres.")
        String name
) {
}
