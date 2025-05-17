package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.PlanMapper;
import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Asset;
import br.edu.gti.gestao_incidentes.enums.Profile;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionPlanService {
    private final ActionPlanRepository planRepository;

    public ActionPlan create(Long areaID, @Validated(OnCreate.class) PlanRequestDTO planRequestDTO) {
        try {
            ActionPlan plan = PlanMapper.toEntity(planRequestDTO);
            return planRepository.save(plan);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ActionPlan> findByAreaId(Long areaId) {
        return planRepository.findByAreaId(areaId);
    }
    public ActionPlan findById(Long id) {
        return planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum plano de ação com com o id: " + id));
    }

    public ActionPlan update(Long id, PlanRequestDTO planRequestDTO) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum plano de ação com com o id: " + id));
        try {
            PlanMapper.applyChanges(planRequestDTO, plan);
            return planRepository.save(plan);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum plano de ação com com o id: " + id));
        planRepository.delete(plan);
    }


}

