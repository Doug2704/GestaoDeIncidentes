package br.edu.gti.gestao_incidentes.config;

import br.edu.gti.gestao_incidentes.service.JwtService;
import br.edu.gti.gestao_incidentes.service.RevokedTokenService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey key;
    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtRevocationFilter jwtRevocationFilter(RevokedTokenService revokedTokenService, JwtService jwtService) {
        return new JwtRevocationFilter(revokedTokenService, jwtService);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtRevocationFilter jwtRevocationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // public routes
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
//
//                        // view access (todos)
//                        .requestMatchers(
//                                "/api/v1/*/find/**", "/api/v1/*/*/*/find/**"
//                        ).permitAll()

                        // ===== USERS =====
                        // Criar usuário (admin)
                        .requestMatchers(HttpMethod.POST, "/api/v1/areas/*/users/create/**")
                        .hasAuthority("SCOPE_ADMIN")
                        // Atualizar usuário (todos)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/areas/*/users/update/**")
                        .hasAnyAuthority("SCOPE_OPERATOR", "SCOPE_MANAGER", "SCOPE_ADMIN")
                        // Deletar usuário (todos)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/areas/*/users/delete/**")
                        .hasAnyAuthority("SCOPE_OPERATOR", "SCOPE_MANAGER", "SCOPE_ADMIN")

                        // ===== PLANS =====
                        // Criar plano (manager)
                        .requestMatchers(HttpMethod.POST, "/api/v1/areas/*/plans/create")
                        .hasAuthority("SCOPE_MANAGER")
                        // Atualizar plano (manager)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/areas/*/plans/update/**")
                        .hasAuthority("SCOPE_MANAGER")
                        // Deletar plano (manager)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/areas/*/plans/delete/**")
                        .hasAuthority("SCOPE_MANAGER")

                        // ===== AREAS =====
                        .requestMatchers("/api/v1/areas/**")
                        .hasAuthority("SCOPE_ADMIN")

                        // ===== ASSETS =====
                        .requestMatchers("/api/v1/assets/**")
                        .hasAuthority("SCOPE_ADMIN")

                        // ===== EXECUTIONS =====
                        // Criar execução (operator)
                        .requestMatchers(HttpMethod.POST, "/api/v1/plans/*/executions/start")
                        .hasAuthority("SCOPE_OPERATOR")
                        // Atualizar execução (operator)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/plans/*/executions/update/**")
                        .hasAuthority("SCOPE_OPERATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/plans/*/executions/finish/**")
                        .hasAuthority("SCOPE_OPERATOR")
                        // Deletar execução (admin)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/plans/*/executions/delete/**")
                        .hasAuthority("SCOPE_ADMIN")

                        // ===== STEPS =====
                        // Criar step (manager)
                        .requestMatchers(HttpMethod.POST, "/api/v1/plans/*/step/create/**")
                        .hasAuthority("SCOPE_MANAGER")
                        // Atualizar step (operator, manager) TODO: verificar setSatus para operador
                        .requestMatchers(HttpMethod.PUT, "/api/v1/plans/*/step/update/**")
                        .hasAnyAuthority("SCOPE_OPERATOR", "SCOPE_MANAGER")
                        // Deletar step (manager)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/plans/*/step/delete/**")
                        .hasAuthority("SCOPE_MANAGER")

                        // ===== TASKS =====
                        // Criar task (manager)
                        .requestMatchers(HttpMethod.POST, "/api/v1/step/*/task/create/**")
                        .hasAuthority("SCOPE_MANAGER")
                        // Atualizar task (operator, manager) TODO: verificar setSatus para operador
                        .requestMatchers(HttpMethod.PUT, "/api/v1/step/*/task/update/**")
                        .hasAnyAuthority("SCOPE_OPERATOR", "SCOPE_MANAGER")
                        // Deletar task (manager)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/step/*/task/delete/**")
                        .hasAuthority("SCOPE_MANAGER")

                        // ===== DELETES GERAIS =====
                        .requestMatchers("/api/v1/*/delete/**", "/api/v1/*/*/*/delete/**")
                        .hasAuthority("SCOPE_ADMIN")

                        // ===== ADMIN PATHS EXCLUSIVOS =====
                        .requestMatchers("/api/v1/admin/**")
                        .hasAuthority("SCOPE_ADMIN")

                        // catch-all
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRevocationFilter, BearerTokenAuthenticationFilter.class)
                .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()));

        return http.build();
    }


    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(key).build();
    }

    //TODO hide variable before deploy
    @Bean
    JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(key).privateKey(priv).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
