package com.example.solicitacoes;

import com.example.solicitacoes.security.JwtFilter;
import com.example.solicitacoes.security.JwtUtil;
import com.example.solicitacoes.service.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SolicitacoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolicitacoesApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtUtil jwtUtil, AuthService authService) {
        JwtFilter jwtFilter = new JwtFilter(jwtUtil, authService);
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>(jwtFilter);
        registrationBean.addUrlPatterns("/solicitacoes/*");
        return registrationBean;
    }
}
