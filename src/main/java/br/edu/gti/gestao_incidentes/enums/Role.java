package br.edu.gti.gestao_incidentes.enums;

public enum Role {

    ADMIN("administradores do projeto");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
