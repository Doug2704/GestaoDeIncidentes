package br.edu.gti.gestao_incidentes.enums;

public enum Profile {

    ADMIN("administrador do sistema"),
    MANAGER("funcionário com maiores permissões"),
    EMPLOYEER("usuário comum"),
    DEFAULT("usuário sem perfil atribuido");

    private final String description;

    Profile(String description) {
        this.description = description.toUpperCase();
    }

    public String getDescription() {
        return description;
    }
}
