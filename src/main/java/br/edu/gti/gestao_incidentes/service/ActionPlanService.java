package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.PlanMapper;
import br.edu.gti.gestao_incidentes.dto.mappers.StepMapper;
import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.dto.requests.StepRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.PlanResponseDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Step;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionPlanService {
    private final ActionPlanRepository planRepository;
    private final AreaRepository areaRepository;
    private final StepService stepService;

    //TODO trabalhar no create antes das entidades filhas
    public PlanResponseDTO create(Long responsibleAreaId, @Validated(OnCreate.class) PlanRequestDTO planRequestDTO) {
        try {
            ActionPlan plan = PlanMapper.toEntity(planRequestDTO);
            Area responsibleArea = areaRepository.findById(responsibleAreaId)
                    .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));
            List<Area> affectedAreas = areaRepository.findAllById(planRequestDTO.affectedAreasIds());
            plan.setAffectedAreas(affectedAreas);
            plan.setResponsibleArea(responsibleArea);
            ActionPlan savedPlan = planRepository.save(plan);

            plan.setSteps(createFirstSteps(savedPlan.getId(), planRequestDTO.stepRequestDTOS()));

            return PlanMapper.toDto(savedPlan);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlanResponseDTO> findByAreaId(Long areaId) {
        return planRepository.findByResponsibleArea_Id(areaId).stream().map(PlanMapper::toDto).toList();
    }

    public PlanResponseDTO findById(Long id) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum plano de ação com com o id: " + id));
        return PlanMapper.toDto(plan);
    }
//
//    public ActionPlan update(Long id, PlanRequestDTO planRequestDTO) {
//        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum plano de ação com com o id: " + id));
//        try {
//            PlanMapper.applyChanges(planRequestDTO, plan);
//            return planRepository.save(plan);
//
//        } catch (DataIntegrityViolationException e) {
//            throw new UniqueFieldViolationException(e);
//        }
//    }

    public void delete(Long id) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum plano de ação com com o id: " + id));
        planRepository.delete(plan);
    }

    private List<Step> createFirstSteps(Long planId, List<StepRequestDTO> stepRequestDTOs) {
        List<Step> steps = new ArrayList<>();
        for (StepRequestDTO stepRequestDTO : stepRequestDTOs) {
            steps.add(StepMapper.toEntity(stepRequestDTO));
            stepService.create(planId, stepRequestDTO);
        }
        return steps;
    }
}

