package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.ActionMapper;
import br.edu.gti.gestao_incidentes.dto.requests.ActionRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.ActionResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Action;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionRepository;
import br.edu.gti.gestao_incidentes.repository.StepRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;
    private final StepRepository stepRepository;
    private final ActionMapper actionMapper;
    private final AuditLogService auditLogService;

    public ActionResponseDTO create(Long stepId, @Validated(OnCreate.class) ActionRequestDTO actionRequestDTO) {
        try {
            Action action = actionMapper.toEntity(actionRequestDTO);
            Step step = stepRepository.findById(stepId).orElseThrow(() -> new NoRegisterException(stepId));
            action.setStep(step);

            return actionMapper.toDto(actionRepository.save(action));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Action> findByStepId(Long stepId) {
        if (!stepRepository.existsById(stepId)) {
            throw new NoRegisterException(stepId);
        }
        return actionRepository.findByStep_Id(stepId);
    }

    public Action findById(Long id) {
        return actionRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
    }

    public Action update(Long id, ActionRequestDTO actionRequestDTO) {
        Action action = actionRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        try {
            if (actionRequestDTO.title() != null) action.setTitle(actionRequestDTO.title());

            return actionRepository.save(action);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(@PathVariable Long id) {
        Action action = actionRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        actionRepository.delete(action);
    }

    public void done(Long id) {
        Action action = actionRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        action.setDone(true);
        actionRepository.save(action);
    }
}

