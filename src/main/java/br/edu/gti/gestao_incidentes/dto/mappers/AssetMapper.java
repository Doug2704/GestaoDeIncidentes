package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.entities.Asset;
import br.edu.gti.gestao_incidentes.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssetMapper {
    private static AreaRepository areaRepository;

    public static Asset toEntity(AssetRequestDTO assetRequestDTO, Long responsibleAreaId) {
        if (assetRequestDTO == null) return null;
        Asset asset = new Asset();
        Area responsibleArea = areaRepository.findById(responsibleAreaId)
                .orElseThrow(() -> new EntityNotFoundException("Área não encontrada"));
        asset.setName(assetRequestDTO.name());
        asset.setResponsibleArea(responsibleArea);

        return asset;
    }
}
