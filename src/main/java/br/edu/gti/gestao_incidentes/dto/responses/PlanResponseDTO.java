package br.edu.gti.gestao_incidentes.dto.responses;

import br.edu.gti.gestao_incidentes.enums.Level;

import java.time.LocalDateTime;
import java.util.List;

public record PlanResponseDTO(
        Long id,
        String title,
        String incidentDescription,
        List<String> steps,
        String responsibleArea,
        List<String> affectedAreas,
        Level urgencyLevel,
        Level impactLevel,
        String planMaxDuration,
        LocalDateTime creationDate

) {
}
