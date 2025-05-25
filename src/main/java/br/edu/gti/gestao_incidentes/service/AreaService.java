package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.AreaMapper;
import br.edu.gti.gestao_incidentes.dto.requests.AreaRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
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

    public Area create(@Validated(OnCreate.class) AreaRequestDTO areaRequestDTO) {
        try {
            Area area = areaMapper.toEntity(areaRequestDTO);
            return areaRepository.save(area);
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

    public Area update(Long id, AreaRequestDTO areaRequestDTO) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        try {
            areaMapper.applyChanges(areaRequestDTO, area);
            return areaRepository.save(area);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Area area = areaRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        areaRepository.delete(area);
    }
}

