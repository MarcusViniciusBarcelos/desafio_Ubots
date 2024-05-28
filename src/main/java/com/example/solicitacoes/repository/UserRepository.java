package com.example.solicitacoes.repository;

import com.example.solicitacoes.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public UserRepository() {
        // Usuário padrão para teste
        users.put("admin", new User("admin", "password"));
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    public void deleteAll() {
        users.clear();
    }

}