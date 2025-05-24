package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionPlanRepository extends JpaRepository<ActionPlan, Long> {
    List<ActionPlan> findByResponsibleArea_Id(Long areaId);
}
