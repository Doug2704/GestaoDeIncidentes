package br.edu.gti.gestao_incidentes.dto.responses;

public record TaskResponseDTO(
        Long id,
        String action,
        boolean isDone
) {
}
