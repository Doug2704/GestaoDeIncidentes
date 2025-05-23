package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.ActionRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.ActionResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.entities.Action;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActionMapper {
    public ActionResponseDTO toDto(Action action) {
        if (action == null) return null;
        return new ActionResponseDTO(
                action.getId(),
                action.getTitle(),
                action.isDone()
        );
    }

    public Action toEntity(ActionRequestDTO actionRequestDTO) {
        Action action = new Action();
        action.setTitle(actionRequestDTO.title());
        return action;
    }

    public List<Action> toEntityList(Step step, List<ActionRequestDTO> actionRequestDTOs) {
        List<Action> actions = new ArrayList<>();
        for (ActionRequestDTO actionRequestDTO : actionRequestDTOs) {
            Action action = toEntity(actionRequestDTO);
            action.setStep(step);
            actions.add(action);
        }
        return actions;
    }
}
