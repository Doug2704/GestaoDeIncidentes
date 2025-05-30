package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.StepMapper;
import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.StepResponseDTO;
import br.edu.gti.gestao_incidentes.entities.Action;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.StepRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;
    private final ActionPlanRepository planRepository;
    private final StepMapper stepMapper;
    private final AuditLogService auditLogService;

    public StepResponseDTO create(Long planId, @Validated(OnCreate.class) StepRequestDTO stepRequestDTO) {
        try {
            Step step = stepMapper.toEntity(stepRequestDTO);
            ActionPlan plan = planRepository.findById(planId)
                    .orElseThrow(() -> new NoRegisterException(planId));
            step.setActionPlan(plan);

            return stepMapper.toDto(stepRepository.save(step));

        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Step> findByPlanId(Long planId) {

        if (!planRepository.existsById(planId)) {
            throw new NoRegisterException(planId);
        }
        return stepRepository.findByActionPlan_Id(planId);
    }

    public Step findById(Long id) {
        return stepRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
    }

    public Step update(Long id, StepRequestDTO stepRequestDTO) {
        Step step = stepRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        try {
            stepMapper.applyChanges(stepRequestDTO, step);
            if (stepRequestDTO.status() != null && areAllActionsDone(step)) step.setStatus(stepRequestDTO.status());
            return stepRepository.save(step);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Step step = stepRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        stepRepository.delete(step);
    }

    public boolean areAllActionsDone(Step step) {
        List<Action> actions = step.getActions();
        for (Action action : actions) {
            if (!action.isDone())// return false;
        throw new RuntimeException("Conclua todas as ações dessa etapa.");
        }
        return true;
    }
}