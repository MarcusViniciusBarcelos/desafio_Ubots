package com.example.solicitacoes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Atendente {
    private String nome;
    private int solicitacoesAtendidas;
    private String tipo;

    public Atendente() {

    }

    @JsonCreator
    public Atendente(@JsonProperty("nome") String nome, @JsonProperty("tipo") String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.solicitacoesAtendidas = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getSolicitacoesAtendidas() {
        return solicitacoesAtendidas;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean podeAtender() {
        return solicitacoesAtendidas < 3;
    }

    public void adicionarSolicitacao() {
        if (podeAtender()) {
            solicitacoesAtendidas++;
        } else {
            throw new RuntimeException("Atendente já está atendendo o número máximo de solicitações.");
        }
    }

    public void removerSolicitacao() {
        if (solicitacoesAtendidas > 0) {
            solicitacoesAtendidas--;
        }
    }
}
