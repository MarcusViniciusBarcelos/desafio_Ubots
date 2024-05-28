package com.example.solicitacoes.service;

import com.example.solicitacoes.model.User;
import com.example.solicitacoes.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testRegisterUser() {
        User user = new User("user", "password");
        doNothing().when(userRepository).save(any(User.class));

        userService.registerUser("user", "password");

        verify(userRepository, times(1)).save(any(User.class));
    }
}