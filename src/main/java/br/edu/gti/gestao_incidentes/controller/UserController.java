package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.user.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.user.UserResponseDTO;
import br.edu.gti.gestao_incidentes.entities.User;
import br.edu.gti.gestao_incidentes.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User savedUser = userService.create(userRequestDTO);
            URI local = URI.create("/" + savedUser.getId());
            return ResponseEntity.created(local).body(savedUser);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            UserResponseDTO foundUser = userService.findById(id);
            return ResponseEntity.ok(foundUser);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO user) {
        try {
            User updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário apagado");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
