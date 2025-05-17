package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Execution;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.enums.Status;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.ExecutionRepository;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    private final ActionPlanRepository planRepository;
    private final UserRepository userRepository;

    public Execution start(Long actionPlanId, Long requesterId) {
        Execution execution = new Execution();
        ActionPlan actionPlan = planRepository.findById(actionPlanId).orElseThrow(() -> new EntityNotFoundException("Plano de Ação não encontrado."));
        startSteps(actionPlan);
        try {
            User requester = userRepository.findById(requesterId).get();

            execution.setRequester(requester);
            execution.setActionPlan(actionPlan);

            return executionRepository.save(execution);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Execution> findByActionPlan(Long planId) {
        return executionRepository.findByActionPlan_id(planId);
    }

    public Execution findById(Long id) {
        return executionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma execução com o id: " + id));
    }

    public Execution finish(Long executionId, Long requesterId) {
        Execution execution = executionRepository.findById(executionId).orElseThrow(() -> new EntityNotFoundException("nenhuma execução com o id: " + executionId));
        try {
            validateAllStepsFinished(execution.getActionPlan());

            User validator = userRepository.findById(requesterId).get();
            execution.setValidator(validator);
            execution.setStatus(Status.FINISHED);
            execution.setFinishDate(LocalDateTime.now());

            return executionRepository.save(execution);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Execution execution = executionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma execução com o id: " + id));
        executionRepository.delete(execution);
    }

    private void startSteps(ActionPlan actionPlan) {
        List<Step> steps = actionPlan.getSteps();
        steps.forEach(step -> step.setStatus(Status.WAITING));
    }

    private void validateAllStepsFinished(ActionPlan actionPlan) {
        List<Step> steps = actionPlan.getSteps();
        for (Step step : steps) {
            if (step.getStatus() != Status.FINISHED)
                throw new RuntimeException("Conclua todas as etapas antes de finalizar o plano de ação");
        }
    }
}

