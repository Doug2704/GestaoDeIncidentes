package br.edu.gti.gestao_incidentes.exceptions;

public class EntityNotFoundException extends jakarta.persistence.EntityNotFoundException {
    public EntityNotFoundException(String entidade){
        super(entidade + " inexistente");
    }
}