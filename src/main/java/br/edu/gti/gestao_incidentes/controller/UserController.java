package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.exceptions.EntityNotFoundException;
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

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuario(@RequestBody User user) {
        try {

            URI local = URI.create("/" + savedUser.getId());
            return ResponseEntity.created(local).body(savedUser);

        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
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

        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> buscarUsuarios() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody User user) {
        try {
            User userAtualizado = userService.updateUser(id, user);
            return ResponseEntity.ok(userAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarUsuario(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário apagado");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
