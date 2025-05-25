package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.responses.ExecutionResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Execution;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExecutionMapper {
    private final PlanMapper planMapper;

    public ExecutionResponseDTO toDto(Execution execution) {
        if (execution == null) return null;
        return new ExecutionResponseDTO(
                execution.getId(),
                execution.getRequester().getName(),
                execution.getValidator().getName(),
                planMapper.toDto(execution.getActionPlan()),
                execution.getOpeningDate(),
                execution.getFinishDate(),
                execution.getStatus()
        );
    }
}
