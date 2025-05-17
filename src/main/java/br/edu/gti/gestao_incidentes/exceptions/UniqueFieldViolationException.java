package br.edu.gti.gestao_incidentes.exceptions;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UniqueFieldViolationException extends RuntimeException {

    private final List<String> messages;

    public UniqueFieldViolationException(DataIntegrityViolationException ex) {
        super(ex.getMessage());
        this.messages = new ArrayList<>();
        // Adicionar a mensagem personalizada de acordo com o campo violado
        if (ex.getMessage().contains("username")) {
            messages.add("Nome de usuário já em uso");
        } else if (ex.getMessage().contains("email")) {
            messages.add("Email já em uso");
        } else {
            messages.add("Erro de integridade de dados");
        }
    }
}


