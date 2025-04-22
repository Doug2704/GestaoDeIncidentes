package br.edu.gti.gestao_incidentes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.enums.Role;
import br.edu.gti.gestao_incidentes.service.UserService;

@Configuration
@Profile("dev")
public class SpringConfiguration implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("test@gmail.com");
        user.setPassword("abc123456");
        user.setRole(Role.ADMIN);

        userService.createUser(user);

    }

}
