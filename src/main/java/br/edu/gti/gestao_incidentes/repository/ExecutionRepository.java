package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    List<Execution> findByActionPlan_id(Long planId);
}
