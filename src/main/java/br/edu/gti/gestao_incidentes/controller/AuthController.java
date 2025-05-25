package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.LoginRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.TokenResponseDTO;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import br.edu.gti.gestao_incidentes.service.JwtService;
import br.edu.gti.gestao_incidentes.service.RevokedTokenService;
import br.edu.gti.gestao_incidentes.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "Authentication", description = "Sessão para realizar login de usuário")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final RevokedTokenService revokedTokenService;

    @PostMapping("/auth/login")
    @Operation(summary = "Realizar login", description = "Se credenciais válidas, retorna um token para validar sessões")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        if (!userRepository.existsByUsername(loginRequest.username()) || !passwordMatcher(loginRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("dados inválidos");
        }

        var auth = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        var authentication = authenticationManager.authenticate(auth);
        String token = jwtService.generateToken(authentication);

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtService.getTokenFromRequest(request);
        revokedTokenService.revokeToken(token);
        return ResponseEntity.ok().build();
    }

    boolean passwordMatcher(LoginRequestDTO loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
        return passwordEncoder.matches(loginRequest.password(), userDetails.getPassword());
    }
}



