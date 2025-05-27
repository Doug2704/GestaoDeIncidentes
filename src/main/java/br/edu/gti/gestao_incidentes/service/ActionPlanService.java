package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.PlanMapper;
import br.edu.gti.gestao_incidentes.dto.requests.PlanRequestDTO;
import br.edu.gti.gestao_incidentes.dto.responses.PlanResponseDTO;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.ActionPlanRepository;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionPlanService {
    private final ActionPlanRepository planRepository;
    private final AreaRepository areaRepository;
    private final PlanMapper planMapper;
    private final AuditLogService auditLogService;

    public PlanResponseDTO create(Long responsibleAreaId, @Validated(OnCreate.class) PlanRequestDTO planRequestDTO) {
        try {
            ActionPlan plan = planMapper.toEntity(planRequestDTO);
            Area responsibleArea = areaRepository.findById(responsibleAreaId)
                    .orElseThrow(() -> new NoRegisterException(responsibleAreaId));
            plan.setResponsibleArea(responsibleArea);

            return planMapper.toDto(planRepository.save(plan));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlanResponseDTO> findByAreaId(Long areaId) {
        if (!areaRepository.existsById(areaId)) {
            throw new NoRegisterException(areaId);
        }
        return planRepository.findByResponsibleArea_Id(areaId).stream().map(planMapper::toDto).toList();
    }

    public PlanResponseDTO findById(Long id) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        return planMapper.toDto(plan);
    }

    public ActionPlan update(Long id, PlanRequestDTO planRequestDTO) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        try {
            planMapper.applyChanges(planRequestDTO, plan);
            return planRepository.save(plan);

        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        ActionPlan plan = planRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        planRepository.delete(plan);
    }
}

