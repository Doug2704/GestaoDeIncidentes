package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.UserRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.UserResponseDTO;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.service.UserService;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import br.edu.gti.gestao_incidentes.validation.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/areas/{actuationAreaId}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@PathVariable Long actuationAreaId, @RequestBody @Validated(OnCreate.class) UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO savedUser = userService.create(actuationAreaId, userRequestDTO);
            URI local = URI.create("/" + savedUser.id());
            return ResponseEntity.created(local).body(savedUser);

        } catch (UniqueFieldViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            UserResponseDTO foundUser = userService.findById(id);
            return ResponseEntity.ok(foundUser);

        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<UserResponseDTO>> findByAreaId(@PathVariable Long actuationAreaId) {
        List<UserResponseDTO> assets = userService.findByAreaId(actuationAreaId);
        return ResponseEntity.ok(assets);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) UserRequestDTO user) {
        try {
            UserResponseDTO updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (UniqueFieldViolationException | NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário excluído");
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
