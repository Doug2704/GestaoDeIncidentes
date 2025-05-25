package br.edu.gti.gestao_incidentes.exceptions;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class UniqueFieldViolationException extends DataIntegrityViolationException {

    private final String fieldName;

    public UniqueFieldViolationException(DataIntegrityViolationException ex) {
        super(ex.getMessage());

        // Expressão regular para extrair o nome do campo da mensagem do PostgreSQL
        String regex = "Detalhe: Chave \\((.*?)\\)=";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ex.getMessage());

        if (matcher.find()) {
            this.fieldName = matcher.group(1);
        } else {
            this.fieldName = "campo_desconhecido";
        }
    }

    @Override
    public String getMessage() {
        return String.format("O campo '%s' já está em uso.", fieldName);
    }
}
