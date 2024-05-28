package com.example.solicitacoes.service;

import com.example.solicitacoes.model.Atendente;
import com.example.solicitacoes.model.Atribuicao;
import com.example.solicitacoes.model.Solicitacao;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DistribuidorDeSolicitacoes {
    private Map<String, List<Atendente>> timesDeAtendimento;
    private Map<String, Queue<Solicitacao>> filasDeEspera;
    private List<Atribuicao> historicoAtribuicoes;

    public DistribuidorDeSolicitacoes() {
        timesDeAtendimento = new HashMap<>();
        filasDeEspera = new HashMap<>();
        historicoAtribuicoes = new ArrayList<>();

        // Inicializa os times de atendimento e as filas de espera
        timesDeAtendimento.put("Cartões", new ArrayList<>());
        timesDeAtendimento.put("Empréstimos", new ArrayList<>());
        timesDeAtendimento.put("Outros Assuntos", new ArrayList<>());

        filasDeEspera.put("Cartões", new LinkedList<>());
        filasDeEspera.put("Empréstimos", new LinkedList<>());
        filasDeEspera.put("Outros Assuntos", new LinkedList<>());
    }

    public void adicionarAtendente(String tipo, Atendente atendente) {
        timesDeAtendimento.get(tipo).add(atendente);
    }

    public void adicionarSolicitacao(Solicitacao solicitacao) {
        String tipo = solicitacao.getTipo();
        List<Atendente> atendentes = timesDeAtendimento.get(tipo);

        Atendente atendenteDisponivel = atendentes.stream()
                .filter(Atendente::podeAtender)
                .findFirst()
                .orElse(null);

        if (atendenteDisponivel != null) {
            atendenteDisponivel.adicionarSolicitacao();
            solicitacao.setStatus("em atendimento");
            historicoAtribuicoes.add(new Atribuicao(atendenteDisponivel.getNome(), solicitacao.getDescricao()));
        } else {
            solicitacao.setStatus("na fila");
            filasDeEspera.get(tipo).add(solicitacao);
        }
    }

    public void liberarAtendente(Atendente atendente) {
        atendente.removerSolicitacao();
        String tipo = encontrarTipoAtendimento(atendente);

        Queue<Solicitacao> fila = filasDeEspera.get(tipo);
        if (fila != null && !fila.isEmpty()) {
            Solicitacao solicitacao = fila.poll();
            solicitacao.setStatus("em atendimento");
            atendente.adicionarSolicitacao();
            historicoAtribuicoes.add(new Atribuicao(atendente.getNome(), solicitacao.getDescricao()));
        }
    }

    private String encontrarTipoAtendimento(Atendente atendente) {
        for (Map.Entry<String, List<Atendente>> entry : timesDeAtendimento.entrySet()) {
            if (entry.getValue().contains(atendente)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public List<Atendente> listarAtendentes(String nomeTime) {
        return timesDeAtendimento.getOrDefault(nomeTime, new LinkedList<>());
    }

    public List<Solicitacao> listarSolicitacoes(String nomeTime) {
        return new ArrayList<>(filasDeEspera.getOrDefault(nomeTime, new LinkedList<>()));
    }

    public Atendente getAtendente(String nome, String tipoAtendimento) {
        return timesDeAtendimento.get(tipoAtendimento).stream()
                .filter(atendente -> atendente.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    public List<Atribuicao> getHistoricoAtribuicoes() {
        return historicoAtribuicoes;
    }
}