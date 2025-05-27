package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.ActionRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.ActionResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Action;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.service.ActionService;
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
@RequestMapping("api/v1/steps/{stepId}/tasks")
@RequiredArgsConstructor
public class ActionController {
    private final ActionService actionService;

    //TODO verificar os paths em acordo com o padrão http
    @PostMapping("/create")
    public ResponseEntity<?> createAction(@PathVariable Long stepId, @RequestBody @Validated(OnCreate.class) ActionRequestDTO actionRequestDTO) {
        try {
            ActionResponseDTO savedAction = actionService.create(stepId, actionRequestDTO);
            URI local = URI.create("/" + savedAction.id());
            return ResponseEntity.created(local).body(savedAction);

        } catch (UniqueFieldViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Action foundAction = actionService.findById(id);
            return ResponseEntity.ok(foundAction);

        } catch (UniqueFieldViolationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Action>> findByStepId(@PathVariable Long stepId) {
        List<Action> actions = actionService.findByStepId(stepId);
        return ResponseEntity.ok(actions);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAction(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) ActionRequestDTO actionRequestDTO) {
        try {
            Action action = actionService.update(id, actionRequestDTO);
            return ResponseEntity.ok(action);
        }catch (UniqueFieldViolationException | NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAction(@PathVariable Long id) {
        try {
            actionService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ação excluída");
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/done/{id}")
    public ResponseEntity<?> done(@PathVariable Long id) {
        try {
            actionService.done(id);
            return ResponseEntity.ok().body("Ação concluída");
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
