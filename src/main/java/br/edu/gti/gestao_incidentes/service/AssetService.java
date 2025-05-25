package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.AssetMapper;
import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Asset;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import br.edu.gti.gestao_incidentes.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;
    private final AreaRepository areaRepository;
    private final AssetMapper assetMapper;

    public Asset create(Long responsibleAreaId, AssetRequestDTO assetRequestDTO) {
        try {
            Asset asset = assetMapper.toEntity(assetRequestDTO);
            Area responsibleArea = areaRepository.findById(responsibleAreaId)
                    .orElseThrow(() -> new NoRegisterException(responsibleAreaId));
            asset.setResponsibleArea(responsibleArea);

            return assetRepository.save(asset);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Asset> findByAreaId(Long responsibleAreaId) {
        if (!areaRepository.existsById(responsibleAreaId)) {
            throw new NoRegisterException(responsibleAreaId);
        }
        return assetRepository.findByResponsibleArea_Id(responsibleAreaId);
    }

    public Asset findById(Long id) {
        return assetRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
    }

    public Asset update(Long id, AssetRequestDTO assetRequestDTO) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        try {
            if (assetRequestDTO.name() != null) asset.setName(assetRequestDTO.name());
            return assetRepository.save(asset);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new NoRegisterException(id));
        assetRepository.delete(asset);
    }
}

