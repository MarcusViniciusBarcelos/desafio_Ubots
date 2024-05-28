package com.example.solicitacoes;

public class Solicitacao {
    private String tipo;
    private String descricao;

    public Solicitacao(String tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }
}
