package br.edu.gti.gestao_incidentes.enums;

public enum Level {
    LOW("Apenas orientação"),
    MEDIUM("Menor urgência, pequenas medidas a serem tomadas"),
    HIGH("Urgência considerável, requer certa agilidade para solucionar");

    private final String description;

    Level(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description.toUpperCase();
    }
    }
