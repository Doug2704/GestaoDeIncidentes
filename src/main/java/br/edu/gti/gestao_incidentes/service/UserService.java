package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.exceptions.EntidadeNaoEncontradaException;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public User createUser(User user) {

        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime localDateTime = ZonedDateTime.now(zoneId).toLocalDateTime();
        user.setRegistrationDate(localDateTime);

        return userRepository.save(user);
    }

    public User findById(Long id) {
        User usuarioEncontrado = userRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("entidade"));
        return usuarioEncontrado;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        User usuarioEncontrado = userRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("entidade"));

        if (user.getName() != null) usuarioEncontrado.setName(user.getName());
        if (user.getEmail() != null) usuarioEncontrado.setEmail(user.getEmail());
        if (user.getPassword() != null) usuarioEncontrado.setPassword(user.getPassword());

        return userRepository.save(usuarioEncontrado);
    }

    public void deleteUser(Long id) {
        User usuarioEncontrado = userRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("entidade"));
        userRepository.delete(usuarioEncontrado);
    }
}