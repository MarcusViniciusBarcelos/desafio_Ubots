package com.example.solicitacoes.controller;

import com.example.solicitacoes.model.User;
import com.example.solicitacoes.repository.UserRepository;
import com.example.solicitacoes.service.AuthService;
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

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
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
}