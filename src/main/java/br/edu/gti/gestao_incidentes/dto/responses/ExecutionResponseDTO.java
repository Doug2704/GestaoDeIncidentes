package br.edu.gti.gestao_incidentes.dto.responses;

import br.edu.gti.gestao_incidentes.enums.Status;

import java.time.LocalDateTime;

public record ExecutionResponseDTO(
        Long id,
        String requester,
        String validator,
        PlanResponseDTO planResponseDTO,
        LocalDateTime openingDate,
        LocalDateTime finishDate,
        Status status
) {
}
