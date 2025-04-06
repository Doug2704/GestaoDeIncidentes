package br.edu.gti.gestao_incidentes.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class EntidadeNaoEncontradaException extends EntityNotFoundException {
    public EntidadeNaoEncontradaException(String entidade){
        super(entidade + " inexistente");
    }
}
