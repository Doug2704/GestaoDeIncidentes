package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Asset;

public class AssetMapper {

    public static Asset toEntity(AssetRequestDTO assetRequestDTO) {
        if (assetRequestDTO == null) return null;
        Asset asset = new Asset();

        asset.setName(assetRequestDTO.name());
        asset.setResponsibleArea(assetRequestDTO.responsibleArea());

        return asset;
    }

    public static void applyChanges(AssetRequestDTO assetRequestDTO, Asset asset) {
        if (assetRequestDTO.name() != null) asset.setName(assetRequestDTO.name());
        if (assetRequestDTO.responsibleArea() != null) asset.setResponsibleArea(assetRequestDTO.responsibleArea());
    }
}
