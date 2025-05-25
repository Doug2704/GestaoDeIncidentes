package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}

