package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public UserRepository userRepository;

}