package com.example.solicitacoes.config;

import com.example.solicitacoes.model.Atendente;
import com.example.solicitacoes.model.Atribuicao;
import com.example.solicitacoes.model.Solicitacao;
import com.example.solicitacoes.service.DistribuidorDeSolicitacoes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DistribuidorDeSolicitacoes distribuidorDeSolicitacoes;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, List<Object>> data = mapper.readValue(new ClassPathResource("data.json").getFile(), new TypeReference<Map<String, List<Object>>>() {});

            List<Atendente> atendentes = mapper.convertValue(data.get("atendentes"), new TypeReference<List<Atendente>>(){});
            List<Solicitacao> solicitacoes = mapper.convertValue(data.get("solicitacoes"), new TypeReference<List<Solicitacao>>(){});
            List<Atribuicao> historicoAtribuicoes = mapper.convertValue(data.get("historicoAtribuicoes"), new TypeReference<List<Atribuicao>>(){});

            for (Atendente atendente : atendentes) {
                distribuidorDeSolicitacoes.adicionarAtendente(atendente.getTipo(), atendente);
            }

            for (Solicitacao solicitacao : solicitacoes) {
                distribuidorDeSolicitacoes.adicionarSolicitacao(solicitacao);
            }

            for (Atribuicao atribuicao : historicoAtribuicoes) {
                distribuidorDeSolicitacoes.getHistoricoAtribuicoes().add(atribuicao);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}