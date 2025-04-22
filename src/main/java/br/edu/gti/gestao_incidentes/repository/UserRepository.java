package br.edu.gti.gestao_incidentes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.gti.gestao_incidentes.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
