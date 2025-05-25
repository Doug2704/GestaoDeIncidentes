package br.edu.gti.gestao_incidentes.dto.responses;

public record SimpleEntityDTO(
        Long id,
        String type,
        String name
) {
}
