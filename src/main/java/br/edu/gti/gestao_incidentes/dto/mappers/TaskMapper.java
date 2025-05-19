package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.TaskRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Task;

public class TaskMapper {

    public static Task toEntity(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO == null) return null;
        Task task = new Task();

        task.setAction(taskRequestDTO.action());

        return task;
    }
}
