package br.edu.gti.gestao_incidentes.repository;

import br.edu.gti.gestao_incidentes.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByActuationArea_Id(Long areaId);

    boolean existsByUsername(String username);
}

