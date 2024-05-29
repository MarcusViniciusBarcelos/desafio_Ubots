package com.example.solicitacoes.controller;

import com.example.solicitacoes.model.Atendente;
import com.example.solicitacoes.model.Solicitacao;
import com.example.solicitacoes.model.User;
import com.example.solicitacoes.repository.UserRepository;
import com.example.solicitacoes.service.AuthService;
import com.example.solicitacoes.service.DistribuidorDeSolicitacoes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SolicitacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private DistribuidorDeSolicitacoes distribuidorDeSolicitacoes;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        // Adiciona o usuário admin ao repositório para garantir que ele exista antes de autenticar
        userRepository.save(new User("admin", "password"));

        // Adiciona atendentes para o teste
        distribuidorDeSolicitacoes.adicionarAtendente("cartoes", new Atendente("Atendente 1", "cartoes"));
    }




    @Test
    public void testRegisterUser() throws Exception {
        User user = new User("user", "password");
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/solicitacoes/register")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário registrado com sucesso"));
    }

    @Test
    public void testLoginUser() throws Exception {
        User user = new User("user", "password");
        userRepository.save(user);
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/solicitacoes/login")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessProtectedResourceWithToken() throws Exception {
        User user = new User("user", "password");
        userRepository.save(user);
        String token = "Bearer " + authService.authenticate("user", "password");

        mockMvc.perform(get("/solicitacoes/protected")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a protected resource"));
    }

    @Test
    public void testCriarSolicitacao() throws Exception {
        Solicitacao solicitacao = new Solicitacao("cartoes", "Problema com o cartão");
        String solicitacaoJson = objectMapper.writeValueAsString(solicitacao);
        String token = "Bearer " + authService.authenticate("admin", "password");

        mockMvc.perform(post("/solicitacoes/solicitacao")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(solicitacaoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarAtendentes() throws Exception {
        String token = "Bearer " + authService.authenticate("admin", "password");

        mockMvc.perform(get("/solicitacoes/atendentes/cartoes")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarSolicitacoes() throws Exception {
        String token = "Bearer " + authService.authenticate("admin", "password");

        mockMvc.perform(get("/solicitacoes/solicitacoes/cartoes")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void testLiberarAtendente() throws Exception {
        Atendente atendente = new Atendente("Atendente 1", "cartoes");
        String atendenteJson = objectMapper.writeValueAsString(atendente);
        String token = "Bearer " + authService.authenticate("admin", "password");

        mockMvc.perform(post("/solicitacoes/atendente/liberar/cartoes")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(atendenteJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Atendente liberado e solicitação atribuída da fila, se disponível."));
    }

    @Test
    public void testHistoricoAtribuicoes() throws Exception {
        String token = "Bearer " + authService.authenticate("admin", "password");

        mockMvc.perform(get("/solicitacoes/historico")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    // Teste para verificar a fila de espera
    @Test
    public void testFilaDeEspera() throws Exception {
        String token = "Bearer " + authService.authenticate("admin", "password");

        // Criar 4 solicitações para serem atendidas imediatamente
        for (int i = 1; i <= 4; i++) {
            Solicitacao solicitacao = new Solicitacao("cartoes", "Problema com o cartão " + i);
            String solicitacaoJson = objectMapper.writeValueAsString(solicitacao);

            mockMvc.perform(post("/solicitacoes/solicitacao")
                            .header("Authorization", token)
                            .contentType("application/json")
                            .content(solicitacaoJson))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Solicitação criada com status: em atendimento"));
        }

        // Criar a quinta solicitação que deve ir para a fila
        Solicitacao solicitacao4 = new Solicitacao("cartoes", "Problema com o cartão 4");
        String solicitacaoJson4 = objectMapper.writeValueAsString(solicitacao4);

        mockMvc.perform(post("/solicitacoes/solicitacao")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(solicitacaoJson4))
                .andExpect(status().isOk())
                .andExpect(content().string("Solicitação criada com status: na fila"));

        // Verificar que a quarta solicitação foi para a fila
        mockMvc.perform(get("/solicitacoes/solicitacoes/cartoes")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"tipo\":\"cartoes\",\"descricao\":\"Problema com o cartão 4\",\"status\":\"na fila\"}]"));
    }
}