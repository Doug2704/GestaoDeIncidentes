package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.TaskMapper;
import br.edu.gti.gestao_incidentes.dto.requests.TaskRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Task;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.TaskRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task create(Long stepId, @Validated(OnCreate.class) TaskRequestDTO taskRequestDTO) {
        try {
            Task task = TaskMapper.toEntity(taskRequestDTO, stepId);
            return taskRepository.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Task> findByStepId(Long stepId) {
        return taskRepository.findByStep_Id(stepId);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
    }

    public Task update(Long id, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
        try {
            if (taskRequestDTO.action() != null) task.setAction(taskRequestDTO.action());

            return taskRepository.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
        taskRepository.delete(task);
    }

    public void done(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma tarefa com o id: " + id));
        task.setDone(true);
    }
}

