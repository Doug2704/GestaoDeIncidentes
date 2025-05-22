package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.PlanResponseDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlanMapper {
    private final StepMapper stepMapper;
    private final AreaRepository areaRepository;

    public PlanResponseDTO toDto(ActionPlan plan) {
        if (plan == null) return null;

        return new PlanResponseDTO(
                plan.getId(),
                plan.getTitle(),
                plan.getIncidentDescription(),
                plan.getResponsibleArea().getName(),
                plan.getAffectedAreas().stream().map(Area::getName).toList(),
                plan.getUrgencyLevel(),
                plan.getImpactLevel(),
                convertLongIntoHours(plan.getPlanMaxDuration()),
                plan.getCreationDate(),
                plan.getSteps().stream().map(stepMapper::toDto).toList()
        );
    }

    public ActionPlan toEntity(PlanRequestDTO planRequestDTO) {
        if (planRequestDTO == null) return null;
        ActionPlan plan = new ActionPlan();
        List<Area> affectedAreas = areaRepository.findAllById(planRequestDTO.affectedAreasIds());

        plan.setTitle(planRequestDTO.title());
        plan.setAffectedAreas(affectedAreas);
        plan.setIncidentDescription(planRequestDTO.incidentDescription());
        plan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        plan.setImpactLevel(planRequestDTO.impactLevel());
        plan.setPlanMaxDuration(planRequestDTO.planMaxDuration());
        plan.setSteps(stepMapper.toEntityList(plan, planRequestDTO.stepRequestDTOs()));

        return plan;
    }

    public void applyChanges(PlanRequestDTO planRequestDTO, ActionPlan actionPlan) {
        if (planRequestDTO.title() != null) actionPlan.setTitle(planRequestDTO.title());
        if (planRequestDTO.stepRequestDTOs() != null) actionPlan.setSteps(
                planRequestDTO.stepRequestDTOs().stream().map(stepMapper::toEntity).toList());
        if (planRequestDTO.incidentDescription() != null)
            actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
        if (planRequestDTO.urgencyLevel() != null) actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        if (planRequestDTO.impactLevel() != null) actionPlan.setImpactLevel(planRequestDTO.impactLevel());
        if (planRequestDTO.planMaxDuration() != null) actionPlan.setPlanMaxDuration(planRequestDTO.planMaxDuration());
    }

    private String convertLongIntoHours(Long minutes) {
        long hour = minutes / 60;
        long remainingMinutes = minutes % 60;
        if (minutes % 60 == 0) return hour + "h";
        else return hour + "h " + remainingMinutes + "m";
    }
}