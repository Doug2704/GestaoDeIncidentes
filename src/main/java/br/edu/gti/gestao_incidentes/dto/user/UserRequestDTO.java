package br.edu.gti.gestao_incidentes.dto.user;

import br.edu.gti.gestao_incidentes.enums.Area;
import br.edu.gti.gestao_incidentes.enums.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Utilize apenas letras no nome")
        @Size(min = 2, message = "Nome precisa ter, pelo menos, {min} caracteres.")
        String name,

        @NotBlank(message = "Nome de usuário é obrigatório, ele será usado em seu login")
        @Size(min = 2, message = "Nome de usuário precisa ter, pelo menos, {min} caracteres.")
        String username,

        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password,

        Area actuationArea,

        Profile profile

) {
}
