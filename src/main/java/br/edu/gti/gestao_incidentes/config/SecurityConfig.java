package br.edu.gti.gestao_incidentes.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //public routes
                        .requestMatchers(
                                "/api/v1/auth/login", "/v3/api-docs/**"
                                , "/swagger-ui/**", "/swagger-ui.html",
                                "/api/v1/plans/all", "/api/v1/plans/find").permitAll()

                        //only admin
                        .requestMatchers(
                                "/api/v1/admin/**", "/api/v1/assets/**",
                                "/api/v1/areas/**", "/api/v1/users/**",
                                "/api/v1/plans/all", "/api/v1/plans/find",
                                "/api/v1/plans/delete").hasAuthority("SCOPE_ADMIN")

                        //only manager
                        .requestMatchers("/api/v1/steps/**",
                                "/api/v1/plans/create", "/api/v1/plans/find",
                                "/api/v1/plans/all", "/api/v1/plans/update"
                        ).hasAuthority("SCOPE_MANAGER")

                        //only operator
                        .requestMatchers(
                                "/api/v1/plans/find", "/api/v1/plans/all",
                                "/api/v1/exec/**"
                        ).hasAuthority("SCOPE_MANAGER")

                        //any other routes that need authentication
                        .anyRequest().authenticated()
                )
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
