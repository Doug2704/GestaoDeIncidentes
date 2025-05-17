package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
