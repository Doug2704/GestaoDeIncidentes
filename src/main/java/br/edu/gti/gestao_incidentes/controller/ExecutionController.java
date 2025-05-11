package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Execution;
import br.edu.gti.gestao_incidentes.service.ExecutionService;
import br.edu.gti.gestao_incidentes.service.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/executions")
@RequiredArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;

    private final Long userId = JwtService.getUserIdFromToken();
    @PostMapping("/start")
    public ResponseEntity<?> startExecution(ActionPlan actionPlan) {
        try {
            Execution savedExecution = executionService.start(actionPlan, userId);
            URI local = URI.create("/" + savedExecution.getId());
            return ResponseEntity.created(local).body(savedExecution);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Execution foundExecution = executionService.findById(id);
            return ResponseEntity.ok(foundExecution);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Execution>> findAll() {
        List<Execution> executions = executionService.findAll();
        return ResponseEntity.ok(executions);
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity<?> finishExecution(@PathVariable Long executionId) {

        try {
            Execution updatedexecution = executionService.finish(executionId, userId);
            return ResponseEntity.ok(updatedexecution);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExecution(@PathVariable Long id) {
        try {
            executionService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Execução excluída");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
