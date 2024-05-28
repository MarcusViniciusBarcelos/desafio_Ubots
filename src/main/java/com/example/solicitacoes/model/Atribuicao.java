package com.example.solicitacoes.model;

import java.util.Date;

public class Atribuicao {
    private String atendente;
    private String solicitacao;
    private Date dataHora;

    public Atribuicao(String atendente, String solicitacao) {
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
