package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.entities.RevokedToken;
import br.edu.gti.gestao_incidentes.repository.RevokedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {
    private final RevokedTokenRepository revokedTokenRepository;

    public void revokeToken(String token) {
        if (!revokedTokenRepository.existsByToken(token)) {
            RevokedToken revokedToken = new RevokedToken();
            revokedToken.setToken(token);
            revokedTokenRepository.save(revokedToken);
        }
    }

   public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }
}
