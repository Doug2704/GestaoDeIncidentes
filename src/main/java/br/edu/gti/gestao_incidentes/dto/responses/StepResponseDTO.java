package br.edu.gti.gestao_incidentes.dto.responses;

import br.edu.gti.gestao_incidentes.enums.Status;

import java.util.List;

public record StepResponseDTO(
        Long id,
        String title,
        String responsibleArea,
        Status status,
        List<ActionResponseDTO> actionResponseDTOs
) {
}
