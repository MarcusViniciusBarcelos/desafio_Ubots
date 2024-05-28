package com.example.solicitacoes.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Atendente {
    private String nome;
    private int solicitacoesAtendidas;

    public Atendente() {

    }

    @JsonCreator
    public Atendente(String nome) {
        this.nome = nome;
        this.solicitacoesAtendidas = 0;

    }

    public String getNome() {
        return nome;
    }

    public int getSolicitacoesAtendidas() {
        return solicitacoesAtendidas;
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
