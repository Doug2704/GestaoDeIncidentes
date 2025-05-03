package br.edu.gti.gestao_incidentes.enums;

public enum Area {
    TI("Setor de tecnologia e infraestrutura de redes"),

    FINANCEIRO("Setor de compras, vendas, negociações, etc"),

    GERENCIA("Gerência da empresa"),

    ADMINISTRATIVO("Setor responsável por questões administrativas e jurídicas"),

    DEFAULT("Para usários sem área definida");

    private final String descricao;

    Area(String descricao) {
        this.descricao = descricao.toUpperCase();
    }

    public String getDescricao() {
        return descricao;
    }
}
