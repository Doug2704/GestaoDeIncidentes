package br.edu.gti.gestao_incidentes.entities;

import java.time.LocalDateTime;

public class Incidente {
    private Long id;
    private String nome;
    private String problema;
    private setor setorAfetado; // Douglas vai criar
    private User solicitante;
    private User validador;
    private Nivel nivel;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

    // Getter para id
    public Long getId() {
        return id;
    }

    // Getter e Setter para nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter para problema
    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    // Getter e Setter para setorAfetado
    public setor getSetorAfetado() {
        return setorAfetado;
    }

    public void setSetorAfetado(setor setorAfetado) {
        this.setorAfetado = setorAfetado;
    }

    // Getter e Setter para solicitante
    public User getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(User solicitante) {
        this.solicitante = solicitante;
    }

    // Getter e Setter para validador
    public User getValidador() {
        return validador;
    }

    public void setValidador(User validador) {
        this.validador = validador;
    }

    // Getter e Setter para nivel
    public nivel getNivel() {
        return nivel;
    }

    public void setNivel(nivel nivel) {
        this.nivel = nivel;
    }

    // Getter e Setter para dataAbertura
    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    // Getter e Setter para dataFechamento
    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }
}
