package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.enums.Profile;
import br.edu.gti.gestao_incidentes.service.ActionPlanService;
import br.edu.gti.gestao_incidentes.service.JwtService;
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
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
public class ActionPlanController {
    private final ActionPlanService planService;

    @PostMapping("/create")
    public ResponseEntity<?> createPlan(@RequestBody @Validated(OnCreate.class) PlanRequestDTO planRequestDTO) {
        try {
            ActionPlan savedPlan = planService.create(planRequestDTO);
            URI local = URI.create("/" + savedPlan.getId());
            return ResponseEntity.created(local).body(savedPlan);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ActionPlan foundPlan = planService.findById(id);
            return ResponseEntity.ok(foundPlan);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActionPlan>> findAll() {
        List<ActionPlan> plans = planService.findAll();
        return ResponseEntity.ok(plans);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) PlanRequestDTO planRequestDTO) {
        try {
            ActionPlan actionPlan = planService.update(id, planRequestDTO);
            return ResponseEntity.ok(actionPlan);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        try {
            planService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Plano de ação excluído");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
