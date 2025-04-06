package br.edu.gti.gestao_incidentes.enums;

public enum Role {

    ADMIN("administrador do sistema"),
    GERENTE("funcionário com maiores permissões"),
    FUNCIONARIO("usuário comum");

    private final String description;

    Role(String description) {
        this.description = description.toUpperCase();
    }

    public String getDescription() {
        return description;
    }
}
