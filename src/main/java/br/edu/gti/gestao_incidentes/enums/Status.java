package br.edu.gti.gestao_incidentes.enums;

public enum Status {
    NOT_STARTED("não iniciado"),
    WAITING("aguardando"),
    EXECUTING("executando"),
    FINISHED("concluído");

    private final String description;

    Status(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
