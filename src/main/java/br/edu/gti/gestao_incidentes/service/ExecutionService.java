package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Execution;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.enums.Status;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
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
    private final UserRepository userRepository;

    public Execution start(ActionPlan actionPlan, Long requesterId) {
        Execution execution = new Execution();
        try {
            User requester = userRepository.findById(requesterId).get();
            execution.setRequester(requester);
            execution.setActionPlan(actionPlan);

            return executionRepository.save(execution);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Execution> findAll() {
        return executionRepository.findAll();
    }

    public Execution findById(Long id) {
        return executionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma execução com o id: " + id));
    }

    public Execution finish(Long executionId, Long requesterId) {
        Execution execution = executionRepository.findById(executionId).orElseThrow(() -> new EntityNotFoundException("nenhuma execução com o id: " + executionId));
        try {
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

}

