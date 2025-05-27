package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.responses.ExecutionResponseDTO;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.service.ExecutionService;
import br.edu.gti.gestao_incidentes.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/plans/{planId}/executions")
@RequiredArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;

    @PostMapping("/start")
    public ResponseEntity<?> startExecution(@PathVariable Long planId) {
        Long userId = JwtService.getUserIdFromToken();
        try {
            ExecutionResponseDTO savedExecution = executionService.start(planId, userId);
            URI local = URI.create("/" + savedExecution.id());
            return ResponseEntity.created(local).body(savedExecution);

        } catch (UniqueFieldViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ExecutionResponseDTO foundExecution = executionService.findById(id);
            return ResponseEntity.ok(foundExecution);

        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findByPlanId(@PathVariable Long planId) {
        List<ExecutionResponseDTO> executions = executionService.findByActionPlan(planId);
        return ResponseEntity.ok(executions);
    }

    @PutMapping("/finish/{executionId}")
    public ResponseEntity<?> finishExecution(@PathVariable Long executionId) {
        Long userId = JwtService.getUserIdFromToken();
        try {
            ExecutionResponseDTO updatedExecution = executionService.finish(executionId, userId);
            return ResponseEntity.ok(updatedExecution);
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCause());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExecution(@PathVariable Long id) {
        try {
            executionService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Execução excluída");
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
