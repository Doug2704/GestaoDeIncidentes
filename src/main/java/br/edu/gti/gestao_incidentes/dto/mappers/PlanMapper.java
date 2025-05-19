package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.PlanResponseDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Step;

public class PlanMapper {


    public static PlanResponseDTO toDto(ActionPlan plan) {
        if (plan == null) return null;

        return new PlanResponseDTO(
                plan.getId(),
                plan.getTitle(),
                plan.getIncidentDescription(),
                plan.getSteps().stream().map(Step::getTitle).toList(), //TODO tratar como response
                plan.getResponsibleArea().getName(),
                plan.getAffectedAreas().stream().map(Area::getName).toList(),
                plan.getUrgencyLevel(),
                plan.getImpactLevel(),
                plan.getPlanMaxDuration().toString(),
                plan.getCreationDate()
        );
    }

    public static ActionPlan toEntity(PlanRequestDTO planRequestDTO) {
        if (planRequestDTO == null) return null;
        ActionPlan actionPlan = new ActionPlan();

        actionPlan.setTitle(planRequestDTO.title());
        actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
        actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        actionPlan.setImpactLevel(planRequestDTO.impactLevel());
        actionPlan.setPlanMaxDuration(planRequestDTO.planMaxDuration());

        return actionPlan;
    }
//
//    public static void applyChanges(PlanRequestDTO planRequestDTO, ActionPlan actionPlan) {
//        if (planRequestDTO.title() != null) actionPlan.setTitle(planRequestDTO.title());
//        if (planRequestDTO.steps() != null) actionPlan.setSteps(planRequestDTO.steps());
//        if (planRequestDTO.affectedAreas() != null) actionPlan.setAffectedAreas(planRequestDTO.affectedAreas());
//        if (planRequestDTO.incidentDescription() != null)
//            actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
//        if (planRequestDTO.urgencyLevel() != null) actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
//        if (planRequestDTO.impactLevel() != null) actionPlan.setImpactLevel(planRequestDTO.impactLevel());
//        if (planRequestDTO.planMaxDuration() != null) actionPlan.setPlanMaxDuration(planRequestDTO.planMaxDuration());
//    }
}
