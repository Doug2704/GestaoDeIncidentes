package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.StepResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.service.StepService;
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
@RequestMapping("api/v1/plans/{planId}/steps")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;

    @PostMapping("/create")
    public ResponseEntity<?> createStep(@PathVariable Long planId, @RequestBody @Validated(OnCreate.class) StepRequestDTO stepRequestDTO) {
        try {
            StepResponseDTO savedStep = stepService.create(planId, stepRequestDTO);
            URI local = URI.create("/" + savedStep.id());
            return ResponseEntity.created(local).body(savedStep);

        } catch (UniqueFieldViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Step foundStep = stepService.findById(id);
            return ResponseEntity.ok(foundStep);

        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Step>> findByPlanId(@PathVariable Long planId) {
        List<Step> steps = stepService.findByPlanId(planId);
        return ResponseEntity.ok(steps);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateStep(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) StepRequestDTO stepRequestDTO) {
        try {
            Step actionStep = stepService.update(id, stepRequestDTO);
            return ResponseEntity.ok(actionStep);
        } catch (UniqueFieldViolationException | NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteStep(@PathVariable Long id) {
        try {
            stepService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Etapa excluída");
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
