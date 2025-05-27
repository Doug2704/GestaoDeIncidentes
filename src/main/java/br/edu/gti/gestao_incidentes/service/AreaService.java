package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.AreaMapper;
import br.edu.gti.gestao_incidentes.dto.requests.AreaRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;
    private final AuditLogService auditLogService;

    public Area create(@Validated(OnCreate.class) AreaRequestDTO areaRequestDTO, Long userId) {
        try {
            Area area = areaRepository.save(areaMapper.toEntity(areaRequestDTO));
            auditLogService.log(userId, "criou", area);
            return (area);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    public Area findById(Long id) {
        return areaRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
    }

    //TODO resolver logs de update com base em qual dado foi alterado
    public Area update(Long areaId, AreaRequestDTO areaRequestDTO) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new NoRegisterException(areaId));
        try {
            areaMapper.applyChanges(areaRequestDTO, area);
            Area updatedArea = areaRepository.save(area);
            //    auditLogService.log(userId, "alterou dados de ", area);
            return updatedArea;
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id, Long userId) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        auditLogService.log(userId, "excluiu ", area);
        areaRepository.delete(area);
    }

}

