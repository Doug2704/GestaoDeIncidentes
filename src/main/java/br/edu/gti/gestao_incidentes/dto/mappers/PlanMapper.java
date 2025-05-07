package br.edu.gti.gestao_incidentes.dto.mappers;


import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;

public class PlanMapper {

    public static ActionPlan toEntity(PlanRequestDTO planRequestDTO) {
        if (planRequestDTO == null) return null;
        ActionPlan actionPlan = new ActionPlan();

        actionPlan.setTitle(planRequestDTO.title());
        actionPlan.setSteps(planRequestDTO.steps());
        actionPlan.setResponsibleArea(planRequestDTO.responsibleArea());
        actionPlan.setAffectedAreas(planRequestDTO.affectedAreas());
        actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
        actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        actionPlan.setImpactLevel(planRequestDTO.impactLevel());

        return actionPlan;
    }

    public static void applyChanges(PlanRequestDTO planRequestDTO, ActionPlan actionPlan) {
        if (planRequestDTO.title() != null) actionPlan.setTitle(planRequestDTO.title());
        if (planRequestDTO.steps() != null) actionPlan.setSteps(planRequestDTO.steps());
        if (planRequestDTO.responsibleArea() != null) actionPlan.setResponsibleArea(planRequestDTO.responsibleArea());
        if (planRequestDTO.affectedAreas() != null) actionPlan.setAffectedAreas(planRequestDTO.affectedAreas());
        if (planRequestDTO.incidentDescription() != null) actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
        if (planRequestDTO.urgencyLevel() != null) actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        if (planRequestDTO.impactLevel() != null) actionPlan.setImpactLevel(planRequestDTO.impactLevel());
    }
}
