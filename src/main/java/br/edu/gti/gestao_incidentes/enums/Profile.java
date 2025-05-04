package br.edu.gti.gestao_incidentes.enums;

public enum Profile {

    DEFAULT("usuário sem perfil atribuido"),
    ADMIN("administrador do sistema"),
    MANAGER("funcionário com maiores permissões"),
    EMPLOYEER("funcionário comum");

    private final String description;

    Profile(String description) {
        this.description = description.toUpperCase();
    }

    public String getDescription() {
        return description;
    }
}
