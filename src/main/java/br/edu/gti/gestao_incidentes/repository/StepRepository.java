package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findByActionPlan_Id(Long planId);
}
