package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.TaskMapper;
import br.edu.gti.gestao_incidentes.dto.requests.TaskRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.TaskResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.entities.Task;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.StepRepository;
import br.edu.gti.gestao_incidentes.repository.TaskRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@RequestMapping("api/v1/{stepId}/tasks")
public class TaskService {
    private final TaskRepository taskRepository;
    private final StepRepository stepRepository;
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    public TaskResponseDTO create(@PathVariable Long stepId, @Validated(OnCreate.class) TaskRequestDTO taskRequestDTO) {
        try {
            Task task = taskMapper.toEntity(taskRequestDTO);
            Step step = stepRepository.findById(stepId).orElseThrow(() -> new EntityNotFoundException("Etapa não encontrada"));
            task.setStep(step);

            return taskMapper.toDto(taskRepository.save(task));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    @GetMapping("/find/all")
    public List<Task> findByStepId(@PathVariable Long stepId) {
        return taskRepository.findByStep_Id(stepId);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
    }

    @PutMapping("/update/{id}")
    public Task update(@PathVariable Long id, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
        try {
            if (taskRequestDTO.action() != null) task.setAction(taskRequestDTO.action());

            return taskRepository.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
        taskRepository.delete(task);
    }

    @PostMapping("/done/{id}")
    public void done(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
        task.setDone(true);
    }
}

