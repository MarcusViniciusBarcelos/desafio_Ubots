package com.example.solicitacoes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.solicitacoes.model.Atendente;
import com.example.solicitacoes.model.Solicitacao;
import com.example.solicitacoes.model.TimeDeAtendimento;

@Component
public class DistribuidorDeSolicitacoes {
    private Map<String, TimeDeAtendimento> timesDeAtendimento;

    public DistribuidorDeSolicitacoes() {
        timesDeAtendimento = new HashMap<>();
        timesDeAtendimento.put("cartoes", new TimeDeAtendimento("cartoes"));
        timesDeAtendimento.put("emprestimos", new TimeDeAtendimento("emprestimos"));
        timesDeAtendimento.put("Outros Assuntos", new TimeDeAtendimento("Outros Assuntos"));
    }

    public void adicionarAtendente(String nomeTime, Atendente atendente) {
        TimeDeAtendimento time = timesDeAtendimento.get(nomeTime);
        if (time != null) {
            time.adicionarAtendente(atendente);
        }
    }

    public void adicionarSolicitacao(Solicitacao solicitacao) {
        String tipo = solicitacao.getTipo();
        TimeDeAtendimento time;

        switch (tipo) {
            case "Problemas com cartão":
                time = timesDeAtendimento.get("cartoes");
                break;
            case "contratação de empréstimo":
                time = timesDeAtendimento.get("emprestimos");
                break;
            default:
                time = timesDeAtendimento.get("Outros Assuntos");
                break;
        }

        if (time != null) {
            time.adicionarSolicitacao(solicitacao);
        }
    }

    public void liberarAtendimento(String nomeTime, Atendente atendente) {
        TimeDeAtendimento time = timesDeAtendimento.get(nomeTime);
        if (time != null) {
            time.liberarAtendimento(atendente);
        }
    }

    public List<Atendente> listarAtendentes(String nomeTime) {
        TimeDeAtendimento time = timesDeAtendimento.get(nomeTime);
        if (time != null) {
            return time.getAtendentes();
        }
        return null;
    }

    public List<Solicitacao> listarSolicitacoes(String nomeTime) {
        TimeDeAtendimento time = timesDeAtendimento.get(nomeTime);
        if (time != null) {
            return time.getSolicitacoes();
        }
        return null;
    }
}