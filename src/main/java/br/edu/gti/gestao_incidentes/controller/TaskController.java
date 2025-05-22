package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.TaskRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.TaskResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Task;
import br.edu.gti.gestao_incidentes.service.TaskService;
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
@RequestMapping("api/v1/steps/{stepId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@PathVariable Long stepId, @RequestBody @Validated(OnCreate.class) TaskRequestDTO taskRequestDTO) {
        try {
            TaskResponseDTO savedtask = taskService.create(stepId, taskRequestDTO);
            URI local = URI.create("/" + savedtask.id());
            return ResponseEntity.created(local).body(savedtask);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long stepId) {
        try {
            Task foundtask = taskService.findById(stepId);
            return ResponseEntity.ok(foundtask);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Task>> findByPlanId(@PathVariable Long planId) {
        List<Task> tasks = taskService.findByStepId(planId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) TaskRequestDTO taskRequestDTO) {
        try {
            Task actiontask = taskService.update(id, taskRequestDTO);
            return ResponseEntity.ok(actiontask);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tarefa excluída");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/done/{id}")
    public ResponseEntity<?> done(@PathVariable Long id) {
        try {
            taskService.done(id);
            return ResponseEntity.ok().body("tarefa concluída");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
