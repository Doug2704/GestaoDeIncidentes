package br.edu.gti.gestao_incidentes.dto.mappers;

import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Asset;

public class AssetMapper {

    public static Asset toEntity(AssetRequestDTO assetRequestDTO) {
        if (assetRequestDTO == null) return null;
        Asset asset = new Asset();
        asset.setName(assetRequestDTO.name());

        return asset;
    }
}
