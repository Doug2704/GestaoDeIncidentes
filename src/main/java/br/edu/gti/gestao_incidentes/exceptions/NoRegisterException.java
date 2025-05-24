package br.edu.gti.gestao_incidentes.exceptions;

public class NoRegisterException extends jakarta.persistence.EntityNotFoundException {

    public NoRegisterException(Long id) {
        super("Nenhum registro encontrado com id: " + id);
    }
}
