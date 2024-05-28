package com.example.solicitacoes.controller;

import com.example.solicitacoes.model.Atendente;
import com.example.solicitacoes.model.Atribuicao;
import com.example.solicitacoes.model.Solicitacao;
import com.example.solicitacoes.model.User;
import com.example.solicitacoes.service.AuthService;
import com.example.solicitacoes.service.DistribuidorDeSolicitacoes;
import com.example.solicitacoes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final DistribuidorDeSolicitacoes distribuidorDeSolicitacoes;
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public SolicitacaoController(DistribuidorDeSolicitacoes distribuidorDeSolicitacoes, AuthService authService, UserService userService) {
        this.distribuidorDeSolicitacoes = distribuidorDeSolicitacoes;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.authenticate(user.getUsername(), user.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.registerUser(user.getUsername(), user.getPassword());
        return "Usuário registrado com sucesso";
    }

    @GetMapping("/protected")
    public String protectedResource() {
        return "This is a protected resource";
    }

    @PostMapping("/solicitacao")
    public String criarSolicitacao(@RequestHeader("Authorization") String token, @RequestBody Solicitacao solicitacao) {
        if (authService.authenticateToken(token.substring(7))) { // Remove "Bearer " prefix
            distribuidorDeSolicitacoes.adicionarSolicitacao(solicitacao);
            return "Solicitação criada com status: " + solicitacao.getStatus();
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }

    @PostMapping("/atendente/{nomeTime}")
    public void adicionarAtendente(@RequestHeader("Authorization") String token, @PathVariable String nomeTime, @RequestBody Atendente atendente) {
        if (authService.authenticateToken(token.substring(7))) { // Remove "Bearer " prefix
            distribuidorDeSolicitacoes.adicionarAtendente(nomeTime, atendente);
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }

    @PostMapping("/atendente/liberar/{tipoAtendimento}")
    public String liberarAtendimento(@RequestHeader("Authorization") String token, @PathVariable String tipoAtendimento, @RequestBody Atendente atendente) {
        if (authService.authenticateToken(token.substring(7))) { // Remove "Bearer " prefix
            if (atendente != null) {
                distribuidorDeSolicitacoes.liberarAtendente(atendente);
                return "Atendente liberado e solicitação atribuída da fila, se disponível.";
            } else {
                return "Atendente não encontrado.";
            }
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }

    @GetMapping("/atendentes/{nomeTime}")
    public List<Atendente> listarAtendentes(@RequestHeader("Authorization") String token, @PathVariable String nomeTime) {
        if (authService.authenticateToken(token.substring(7))) { // Remove "Bearer " prefix
            return distribuidorDeSolicitacoes.listarAtendentes(nomeTime);
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }

    @GetMapping("/solicitacoes/{nomeTime}")
    public List<Solicitacao> listarSolicitacoes(@RequestHeader("Authorization") String token, @PathVariable String nomeTime) {
        if (authService.authenticateToken(token.substring(7))) { // Remove "Bearer " prefix
            return distribuidorDeSolicitacoes.listarSolicitacoes(nomeTime);
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }

    @GetMapping("/historico")
    public List<Atribuicao> getHistoricoAtribuicoes(@RequestHeader("Authorization") String token) {
        if (authService.authenticateToken(token.substring(7))) { // Remove "Bearer " prefix
            return distribuidorDeSolicitacoes.getHistoricoAtribuicoes();
        } else {
            throw new RuntimeException("Unauthorized");
        }
    }
}