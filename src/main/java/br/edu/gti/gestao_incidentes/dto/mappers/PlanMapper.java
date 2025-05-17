package br.edu.gti.gestao_incidentes.dto.mappers;


import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;

public class PlanMapper {
    private static AreaRepository areaRepository;

    public static ActionPlan toEntity(PlanRequestDTO planRequestDTO) {
        if (planRequestDTO == null) return null;
        ActionPlan actionPlan = new ActionPlan();
        Area responsibleArea = areaRepository.findById(planRequestDTO.responsibleAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));

        actionPlan.setTitle(planRequestDTO.title());
        actionPlan.setSteps(planRequestDTO.steps());
        actionPlan.setResponsibleArea(responsibleArea);
        actionPlan.setAffectedAreas(planRequestDTO.affectedAreas());
        actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
        actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        actionPlan.setImpactLevel(planRequestDTO.impactLevel());
        actionPlan.setPlanMaxDuration(planRequestDTO.planMaxDuration());

        return actionPlan;
    }

    public static void applyChanges(PlanRequestDTO planRequestDTO, ActionPlan actionPlan) {
        if (planRequestDTO.title() != null) actionPlan.setTitle(planRequestDTO.title());
        if (planRequestDTO.steps() != null) actionPlan.setSteps(planRequestDTO.steps());
        if (planRequestDTO.affectedAreas() != null) actionPlan.setAffectedAreas(planRequestDTO.affectedAreas());
        if (planRequestDTO.incidentDescription() != null)
            actionPlan.setIncidentDescription(planRequestDTO.incidentDescription());
        if (planRequestDTO.urgencyLevel() != null) actionPlan.setUrgencyLevel(planRequestDTO.urgencyLevel());
        if (planRequestDTO.impactLevel() != null) actionPlan.setImpactLevel(planRequestDTO.impactLevel());
        if (planRequestDTO.planMaxDuration() != null) actionPlan.setPlanMaxDuration(planRequestDTO.planMaxDuration());
    }
}
