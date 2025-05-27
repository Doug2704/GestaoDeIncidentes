package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.responses.ExecutionResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Execution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecutionMapper {
    private final PlanMapper planMapper;

    //TODO verificar formatação de horário e data
    public ExecutionResponseDTO toDto(Execution execution) {
        if (execution == null) return null;

        return new ExecutionResponseDTO(
                execution.getId(),
                execution.getRequester().getName(),
                validator(execution),
                planMapper.toDto(execution.getActionPlan()),
                execution.getOpeningDate(),
                execution.getFinishDate(),
                execution.getStatus()
        );
    }

    String validator(Execution execution) {
        if (execution.getValidator() != null) {
            return execution.getValidator().getName();
        }
        return null;
    }
}
