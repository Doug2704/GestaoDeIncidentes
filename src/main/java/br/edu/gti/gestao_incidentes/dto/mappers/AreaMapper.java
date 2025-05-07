package br.edu.gti.gestao_incidentes.dto.mappers;


import br.edu.gti.gestao_incidentes.dto.requests.AreaRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Area;

public class AreaMapper {

    public static Area toEntity(AreaRequestDTO areaRequestDTO) {
        if (areaRequestDTO == null) return null;
        Area area = new Area();

        area.setName(areaRequestDTO.name());

        return area;
    }

    public static void applyChanges(AreaRequestDTO areaRequestDTO, Area area) {
        if (areaRequestDTO.name() != null) area.setName(areaRequestDTO.name());
    }
}
