package com.example.solicitacoes.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class TimeDeAtendimento {
    private String nome;
    private Queue<Atendente> atendentes;
    private Queue<Solicitacao> filaDeEspera;

    public TimeDeAtendimento(String nome) {
        this.nome = nome;
        this.atendentes = new LinkedList<>();
        this.filaDeEspera = new LinkedList<>();
    }

    public void adicionarAtendente(Atendente atendente) {
        atendentes.add(atendente);
    }

    public void adicionarSolicitacao(Solicitacao solicitacao) {
        boolean atendido = false;
        for (Atendente atendente : atendentes) {
            if (atendente.podeAtender()) {
                atendente.adicionarSolicitacao();
                atendido = true;
                break;
            }
        }

        if (!atendido) {
            filaDeEspera.add(solicitacao);
        }
    }

    public void processarFilaDeEspera() {
        while (!filaDeEspera.isEmpty()) {
            Solicitacao solicitacao = filaDeEspera.poll();
            boolean atendido = false;
            for (Atendente atendente : atendentes) {
                if (atendente.podeAtender()) {
                    atendente.adicionarSolicitacao();
                    atendido = true;
                    break;
                }
            }

            if (!atendido) {
                filaDeEspera.add(solicitacao);
                break;
            }
        }
    }

    public void liberarAtendimento(Atendente atendente) {
        atendente.removerSolicitacao();
        processarFilaDeEspera();
    }

    public List<Atendente> getAtendentes() {
        return atendentes.stream().collect(Collectors.toList());
    }

    public List<Solicitacao> getSolicitacoes() {
        return filaDeEspera.stream().collect(Collectors.toList());
    }
}