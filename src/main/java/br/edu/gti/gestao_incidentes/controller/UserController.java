package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.exceptions.EntidadeNaoEncontradaException;
import br.edu.gti.gestao_incidentes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse email já está cadastrado");
        }
        catch (RuntimeException e) {
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

    @GetMapping
    public ResponseEntity<List<User>> buscarUsuarios() {
        List<User> usuarios = userService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody User user) {
        try {
            User usuarioAtualizado = userService.updateUser(id, user);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarUsuario(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário apagado");
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
