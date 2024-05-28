package com.example.solicitacoes.service;

import com.example.solicitacoes.model.User;
import com.example.solicitacoes.repository.UserRepository;
import com.example.solicitacoes.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    public void testAuthenticate() {
        User user = new User("user", "password");
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("token");

        String token = authService.authenticate("user", "password");

        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    public void testAuthenticateWithInvalidCredentials() {
        when(userRepository.findByUsername("user")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> authService.authenticate("user", "password"));
    }
}
