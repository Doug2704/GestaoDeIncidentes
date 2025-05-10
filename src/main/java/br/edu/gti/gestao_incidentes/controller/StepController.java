package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.service.StepService;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import br.edu.gti.gestao_incidentes.validation.OnUpdate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/steps")
@RequiredArgsConstructor
public class StepController {
    private StepService stepService;

    //TODO implementar quem pode criar
    @PostMapping("/create")
    public ResponseEntity<?> createstep(@RequestBody @Validated(OnCreate.class) StepRequestDTO stepRequestDTO) {
        try {
            Step savedStep = stepService.create(stepRequestDTO);
            URI local = URI.create("/" + savedStep.getId());
            return ResponseEntity.created(local).body(savedStep);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Step foundStep = stepService.findById(id);
            return ResponseEntity.ok(foundStep);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Step>> findAll() {
        List<Step> steps = stepService.findAll();
        return ResponseEntity.ok(steps);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatestep(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) StepRequestDTO stepRequestDTO) {
        try {
            Step actionStep = stepService.update(id, stepRequestDTO);
            return ResponseEntity.ok(actionStep);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletestep(@PathVariable Long id) {
        try {
            stepService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Etapa excluída");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
