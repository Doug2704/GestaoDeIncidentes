package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findByStep_Id(Long stepId);
}
