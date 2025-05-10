package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Step;

public class StepMapper {

    public static Step toEntity(StepRequestDTO stepRequestDTO) {
        if (stepRequestDTO == null) return null;
        Step step = new Step();

        step.setTitle(stepRequestDTO.title());
        step.setAction(stepRequestDTO.action());
        step.setResponsibleArea(stepRequestDTO.responsibleArea());
        step.setStatus(stepRequestDTO.status());

        return step;
    }

    public static void applyChanges(StepRequestDTO stepRequestDTO, Step step) {
        if (stepRequestDTO.title() != null) step.setTitle(stepRequestDTO.title());
        if (stepRequestDTO.action() != null) step.setAction(stepRequestDTO.action());
        if (stepRequestDTO.responsibleArea() != null) step.setResponsibleArea(stepRequestDTO.responsibleArea());
        if (stepRequestDTO.status() != null) step.setStatus(stepRequestDTO.status());
    }
}
