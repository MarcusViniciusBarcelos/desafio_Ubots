package com.example.solicitacoes.model;

public class Solicitacao {
    private String tipo;
    private String descricao;
    private String status;

    public Solicitacao(String tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.status = "na fila"; // status inicial
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
