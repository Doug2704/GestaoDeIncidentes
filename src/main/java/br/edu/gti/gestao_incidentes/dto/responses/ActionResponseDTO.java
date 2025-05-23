package br.edu.gti.gestao_incidentes.dto.responses;

public record ActionResponseDTO(
        Long id,
        String title,
        boolean isDone
) {
}
