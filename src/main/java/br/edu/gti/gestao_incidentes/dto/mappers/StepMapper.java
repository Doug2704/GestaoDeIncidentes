package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.StepResponseDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StepMapper {
    private final AreaService areaService;
    public final TaskMapper taskMapper;

    public StepResponseDTO toDto(Step step) {
        if (step == null) return null;
        return new StepResponseDTO(
                step.getId(),
                step.getTitle(),
                step.getResponsibleArea().getName(),
                step.getStatus(),
                step.getTasks().stream().map(taskMapper::toDto).toList()
        );
    }

    public Step toEntity(StepRequestDTO stepRequestDTO) {
        if (stepRequestDTO == null) return null;
        Step step = new Step();
        Area area = areaService.findById(stepRequestDTO.responsibleAreaId());
        step.setTitle(stepRequestDTO.title());
        step.setStatus(stepRequestDTO.status());
        step.setResponsibleArea(area);
        step.setTasks(taskMapper.toEntityList(step, stepRequestDTO.taskRequestDTOs()));

        return step;
    }

    public void applyChanges(StepRequestDTO stepRequestDTO, Step step) {
        if (stepRequestDTO.title() != null) step.setTitle(stepRequestDTO.title());
        if (stepRequestDTO.taskRequestDTOs() != null)
            step.setTasks(taskMapper.toEntityList(step, stepRequestDTO.taskRequestDTOs()));
        if (stepRequestDTO.status() != null) step.setStatus(stepRequestDTO.status());
    }

    public List<Step> toEntityList(ActionPlan plan, List<StepRequestDTO> stepRequestDTOs) {
        List<Step> steps = new ArrayList<>();
        for (StepRequestDTO stepRequestDTO : stepRequestDTOs) {
            Step step = toEntity(stepRequestDTO);
            step.setActionPlan(plan);
            steps.add(step);
        }
        return steps;
    }
}
