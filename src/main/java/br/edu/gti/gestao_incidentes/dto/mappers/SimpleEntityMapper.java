package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.responses.SimpleEntityDTO;
import br.edu.gti.gestao_incidentes.entities.*;
import org.springframework.stereotype.Component;

@Component
public class SimpleEntityMapper {
    public SimpleEntityDTO toDto(Object object) {
        if (object == null) return null;


        if (object instanceof Action action) {
            return new SimpleEntityDTO(
                    action.getId(),
                    "ação",
                    action.getTitle()
            );
        } else if (object instanceof ActionPlan actionPlan) {
            return new SimpleEntityDTO(
                    actionPlan.getId(),
                    "plano de ação",
                    actionPlan.getTitle()
            );
        } else if (object instanceof Area area) {
            return new SimpleEntityDTO(
                    area.getId(),
                    "área",
                    area.getName()
            );
        } else if (object instanceof Asset asset) {
            return new SimpleEntityDTO(
                    asset.getId(),
                    "ativo",
                    asset.getName()
            );
        } else if (object instanceof Execution execution) {
            return new SimpleEntityDTO(
                    execution.getId(),
                    "exeção de",
                    execution.getActionPlan().getTitle()
            );
        } else if (object instanceof Step step) {
            return new SimpleEntityDTO(
                    step.getId(),
                    "etapa",
                    step.getTitle()
            );
        }
        return new SimpleEntityDTO(null, object.getClass().getSimpleName(), "Desconhecido");
    }
}
