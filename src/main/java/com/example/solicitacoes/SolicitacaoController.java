package com.example.solicitacoes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private DistribuidorDeSolicitacoes distribuidorDeSolicitacoes;

    @PostMapping
    public void adicionarSolicitacao(@RequestBody Solicitacao solicitacao) {
        distribuidorDeSolicitacoes.adicionarSolicitacao(solicitacao);
    }

    @PostMapping("/atendente/{nomeTime}")
    public void adicionarAtendente(@PathVariable String nomeTime, @RequestBody Atendente atendente) {
        distribuidorDeSolicitacoes.adicionarAtendente(nomeTime, atendente);
    }

    @PostMapping("/liberar/{nomeTime}")
    public void liberarAtendimento(@PathVariable String nomeTime, @RequestBody Atendente atendente) {
        distribuidorDeSolicitacoes.liberarAtendimento(nomeTime, atendente);
    }

    @GetMapping("/atendentes/{nomeTime}")
    public List<Atendente> listarAtendentes(@PathVariable String nomeTime) {
        return distribuidorDeSolicitacoes.listarAtendentes(nomeTime);
    }

    @GetMapping("/solicitacoes/{nomeTime}")
    public List<Solicitacao> listarSolicitacoes(@PathVariable String nomeTime) {
        return distribuidorDeSolicitacoes.listarSolicitacoes(nomeTime);
    }
}