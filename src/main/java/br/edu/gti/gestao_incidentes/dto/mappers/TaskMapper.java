package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.TaskRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.TaskResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.entities.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {
    public TaskResponseDTO toDto(Task task) {
        if (task == null) return null;
        return new TaskResponseDTO(
                task.getId(),
                task.getAction(),
                task.isDone()
        );
    }

    public Task toEntity(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setAction(taskRequestDTO.action());
        return task;
    }

    public List<Task> toEntityList(Step step, List<TaskRequestDTO> taskRequestDTOs) {
        List<Task> tasks = new ArrayList<>();
        for (TaskRequestDTO taskRequestDTO : taskRequestDTOs) {
            Task task = toEntity(taskRequestDTO);
            task.setStep(step);
            tasks.add(task);
        }
        return tasks;
    }
}
