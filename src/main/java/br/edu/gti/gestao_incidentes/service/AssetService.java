package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.AssetMapper;
import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Asset;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.repository.AssetRepository;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    public Asset create(Long responsibleAreaId, @Validated(OnCreate.class) AssetRequestDTO assetRequestDTO) {
        try {
            Asset asset = AssetMapper.toEntity(assetRequestDTO, responsibleAreaId);
            return assetRepository.save(asset);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public List<Asset> findByAreaId(Long responsibleAreaId) {
        return assetRepository.findByResponsibleArea_Id(responsibleAreaId);
    }

    public Asset findById(Long id) {
        return assetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum ativo com o id: " + id));
    }

    public Asset update(Long id, AssetRequestDTO assetRequestDTO) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum ativo com o id: " + id));
        try {
            if (assetRequestDTO.name() != null) asset.setName(assetRequestDTO.name());
            return assetRepository.save(asset);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueFieldViolationException(e);
        }
    }

    public void delete(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("nenhum ativo com o id: " + id));
        assetRepository.delete(asset);
    }
}

