package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/audit")
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping("/logs")
    public ResponseEntity<?> getLogs() {
        List<String> logs = auditLogService.getLogs();
        return ResponseEntity.ok(logs);
    }
}
