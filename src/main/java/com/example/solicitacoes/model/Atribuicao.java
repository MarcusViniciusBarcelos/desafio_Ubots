package com.example.solicitacoes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Atribuicao {
    private String atendente;
    private String solicitacao;
    private Date dataHora;

    public Atribuicao() {
        // Construtor padrão necessário para a desserialização do Jackson
    }

    @JsonCreator
    public Atribuicao(@JsonProperty("atendente") String atendente, @JsonProperty("solicitacao") String solicitacao) {
        this.atendente = atendente;
        this.solicitacao = solicitacao;
        this.dataHora = new Date();
    }

    public String getAtendente() {
        return atendente;
    }

    public String getSolicitacao() {
        return solicitacao;
    }

    public Date getDataHora() {
        return dataHora;
    }
}
