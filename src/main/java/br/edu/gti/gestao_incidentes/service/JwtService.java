package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.entities.user.UserAuthenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder encoder;

    //TODO implementar função de deslogar
    public String generateToken(Authentication authentication) {
        UserAuthenticated userAuth = (UserAuthenticated) authentication.getPrincipal();
        User user = userAuth.getUser();
        Instant now = Instant.now();
        long expiry = 7200; //2h

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("conexaoestagios")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("id", user.getId())
                .claim("scope", List.of(user.getProfile().getAuthority()))
                .claim("profile", user.getProfile())
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public static Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAuthenticated userAuth) {
            return userAuth.getUser().getId();
        } else if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("id");
        }
        return null;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

}
