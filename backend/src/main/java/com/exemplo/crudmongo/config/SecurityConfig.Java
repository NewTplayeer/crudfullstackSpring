package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita a segurança por anotação (ex: @PreAuthorize)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
            .authorizeHttpRequests(authz -> authz
                // AQUI DEFINIMOS AS REGRAS DA SUA HISTÓRIA DE USUÁRIO
                
                // Regra: ROLE_ALUNO pode apenas listar
                .requestMatchers(HttpMethod.GET, "/api/cursos/").hasAnyRole("ALUNO", "COORDENADOR")
                
                // Regra: Somente ROLE_COORDENADOR pode criar, atualizar ou excluir
                .requestMatchers(HttpMethod.POST, "/api/cursos").hasRole("COORDENADOR")
                .requestMatchers(HttpMethod.PUT, "/api/cursos/").hasRole("COORDENADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/cursos/").hasRole("COORDENADOR")
                
                // Exigir autenticação para qualquer outra requisição
                .anyRequest().authenticated() 
            )
            .httpBasic(); // Habilita autenticação básica (usuário e senha) para testes
        
        return http.build();
    }
}