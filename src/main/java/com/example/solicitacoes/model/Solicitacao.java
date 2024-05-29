package com.example.solicitacoes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Solicitacao {
    private String tipo;
    private String descricao;
    private String status;

    public Solicitacao() {
        // Construtor padrão para a desserialização
    }

    @JsonCreator
    public Solicitacao(@JsonProperty("tipo") String tipo, @JsonProperty("descricao") String descricao) {
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
