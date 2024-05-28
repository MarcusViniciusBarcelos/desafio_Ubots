package com.example.solicitacoes.service;

import com.example.solicitacoes.model.User;
import com.example.solicitacoes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password) {
        userRepository.save(new User(username, password));
    }
}