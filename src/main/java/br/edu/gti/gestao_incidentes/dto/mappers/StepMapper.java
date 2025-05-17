package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;

public class StepMapper {
    private static ActionPlanRepository planRepository;
    private static AreaRepository areaRepository;

    public static Step toEntity(StepRequestDTO stepRequestDTO, Long planId) {
        if (stepRequestDTO == null) return null;
        Step step = new Step();
        ActionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plano de ação não encontrado"));
        Area responsibleArea = areaRepository.findById(stepRequestDTO.responsibleAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));
        step.setTitle(stepRequestDTO.title());

        step.setActionPlan(plan);
        step.setResponsibleArea(responsibleArea);
        step.setTasks(stepRequestDTO.tasks());
        step.setStatus(stepRequestDTO.status());

        return step;
    }

    public static void applyChanges(StepRequestDTO stepRequestDTO, Step step) {
        if (stepRequestDTO.title() != null) step.setTitle(stepRequestDTO.title());
        if (stepRequestDTO.tasks() != null) step.setTasks(stepRequestDTO.tasks());
        if (stepRequestDTO.status() != null) step.setStatus(stepRequestDTO.status());
    }
}
