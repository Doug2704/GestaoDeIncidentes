package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AssetRequestDTO(

        @NotBlank(message = "Nome do ativo é obrigatório", groups = OnCreate.class)
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Utilize apenas letras no nome")
        @Size(min = 2, message = "Nome do ativo precisa ter, pelo menos, {min} caracteres.")
        String name
) {
}
