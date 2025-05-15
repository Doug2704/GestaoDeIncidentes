package br.edu.gti.gestao_incidentes.exceptions;

public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException(String message) {
        super(message);
    }
}
