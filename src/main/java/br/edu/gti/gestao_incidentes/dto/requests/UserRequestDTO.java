package br.edu.gti.gestao_incidentes.dto.requests;

import br.edu.gti.gestao_incidentes.enums.Profile;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import br.edu.gti.gestao_incidentes.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Nome é obrigatório", groups = OnCreate.class)
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Utilize apenas letras no nome",  groups = OnCreate.class)
        @Size(min = 2, message = "Nome precisa ter, pelo menos, {min} caracteres.")
        String name,

        @NotBlank(message = "Nome de usuário é obrigatório, ele será usado em seu login", groups = OnCreate.class)
        @Size(min = 2, message = "Nome de usuário precisa ter, pelo menos, {min} caracteres.")
        String username,

        @Email(message = "Formato de e-mail inválido. Exemplo: usuario@dominio.com", groups = {OnCreate.class, OnUpdate.class})
        @NotBlank(message = "Email é obrigatório", groups = OnCreate.class)
        String email,

        @NotBlank(message = "Senha é obrigatória", groups = OnCreate.class)
        String password,

        Profile profile

) {
}
