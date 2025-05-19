package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.StepMapper;
import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import br.edu.gti.gestao_incidentes.repository.StepRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
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
    private final AreaRepository areaRepository;

    public Step create(Long planId, @Validated(OnCreate.class) StepRequestDTO stepRequestDTO) {
        try {
            Step step = StepMapper.toEntity(stepRequestDTO);
            ActionPlan plan = planRepository.findById(planId)
                    .orElseThrow(() -> new EntityNotFoundException("Plano de ação não encontrado"));
            step.setActionPlan(plan);
            Area responsibleArea = areaRepository.findById(stepRequestDTO.responsibleAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));
            step.setResponsibleArea(responsibleArea);
            return stepRepository.save(step);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Step> findByPlanId(Long planId) {
        return stepRepository.findByActionPlan_Id(planId);
    }

    public Step findById(Long id) {
        return stepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma etapa com o id: " + id));
    }

    public Step update(Long id, StepRequestDTO stepRequestDTO) {
        Step step = stepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma etapa com o id: " + id));
        try {
            StepMapper.applyChanges(stepRequestDTO, step);
            return stepRepository.save(step);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Step step = stepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhuma etapa com o id: " + id));
        stepRepository.delete(step);
    }
}

