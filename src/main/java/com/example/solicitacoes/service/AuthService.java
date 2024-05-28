package com.example.solicitacoes.service;

import com.example.solicitacoes.model.User;
import com.example.solicitacoes.repository.UserRepository;
import com.example.solicitacoes.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(user);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean authenticateToken(String token) {
        try {
            String username = jwtUtil.extractClaims(token).getSubject();
            User user = loadUserByUsername(username);
            return jwtUtil.validateToken(token, user);
        } catch (Exception e) {
            return false;
        }
    }
}