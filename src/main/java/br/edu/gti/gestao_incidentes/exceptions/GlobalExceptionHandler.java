package br.edu.gti.gestao_incidentes.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        UniqueFieldViolationException customEuniqueFieldViolationExceptionception = new UniqueFieldViolationException(ex);
        return new ResponseEntity<>(customEuniqueFieldViolationExceptionception.getMessages(), HttpStatus.BAD_REQUEST);
    }
}
