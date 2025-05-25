package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.ExecutionMapper;
import br.edu.gti.gestao_incidentes.dto.responses.ExecutionResponseDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Execution;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.enums.Status;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.ExecutionRepository;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    private final ActionPlanRepository planRepository;
    private final UserRepository userRepository;
    private final ExecutionMapper executionMapper;

    public ExecutionResponseDTO start(Long actionPlanId, Long requesterId) {
        Execution execution = new Execution();
        ActionPlan actionPlan = planRepository.findById(actionPlanId).orElseThrow(() -> new NoRegisterException(actionPlanId));
        startSteps(actionPlan);
        try {
            User requester = userRepository.findById(requesterId).get();

            execution.setRequester(requester);
            execution.setActionPlan(actionPlan);

            return executionMapper.toDto(executionRepository.save(execution));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<ExecutionResponseDTO> findByActionPlan(Long planId) {
        if (!planRepository.existsById(planId)) {
            throw new NoRegisterException(planId);
        }
        return executionRepository.findByActionPlan_id(planId).stream().map(executionMapper::toDto).toList();
    }

    public ExecutionResponseDTO findById(Long id) {
        Execution foundExecution = executionRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        return executionMapper.toDto(foundExecution);
    }

    public ExecutionResponseDTO finish(Long executionId, Long requesterId) {
        Execution execution = executionRepository.findById(executionId).orElseThrow(() -> new NoRegisterException(executionId));
        try {
            validateAllStepsFinished(execution.getActionPlan());

            User validator = userRepository.findById(requesterId).get();
            execution.setValidator(validator);
            execution.setStatus(Status.FINISHED);
            execution.setFinishDate(LocalDateTime.now());

            return executionMapper.toDto(executionRepository.save(execution));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Execution execution = executionRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        executionRepository.delete(execution);
    }

    private void startSteps(ActionPlan actionPlan) {
        List<Step> steps = actionPlan.getSteps();
        steps.forEach(step -> step.setStatus(Status.WAITING));
    }

    //TODO verificar se todas as ações estão com boolean "done" como true antes de setar finished em step
    private void validateAllStepsFinished(ActionPlan actionPlan) {
        List<Step> steps = actionPlan.getSteps();
        for (Step step : steps) {
            if (step.getStatus() != Status.FINISHED)
                throw new RuntimeException("Conclua todas as etapas antes de finalizar o plano de ação");
        }
    }
}

