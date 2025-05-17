package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.TaskRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.entities.Task;
import br.edu.gti.gestao_incidentes.repository.StepRepository;
import jakarta.persistence.EntityNotFoundException;

public class TaskMapper {
    private static StepRepository stepRepository;

    public static Task toEntity(TaskRequestDTO taskRequestDTO, Long stepId) {
        if (taskRequestDTO == null) return null;
        Task task = new Task();
        Step step = stepRepository.findById(stepId).orElseThrow(() -> new EntityNotFoundException("Etapa não encontrada"));
        task.setAction(taskRequestDTO.action());
        task.setStep(step);

        return task;
    }
}
