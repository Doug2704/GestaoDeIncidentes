package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.exceptions.EntidadeNaoEncontradaException;
import br.edu.gti.gestao_incidentes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody User user) {
        try {
            User usuarioSalvo = userService.createUser(user);
            URI local = URI.create("/" + usuarioSalvo.getId());
            return ResponseEntity.created(local).body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPeloID(@PathVariable Long id) {
        try {
            User usuarioEncontrado = userService.findById(id);
            return ResponseEntity.ok(usuarioEncontrado);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
