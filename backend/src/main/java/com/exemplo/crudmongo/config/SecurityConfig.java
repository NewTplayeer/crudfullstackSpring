package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // habilita @PreAuthorize
public class SecurityConfig {

    // Define usuários em memória (somente para testes)
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails coordenador = User.withUsername("coordenador")
                .password("{noop}123") // {noop} => senha sem criptografia
                .roles("COORDENADOR")
                .build();

        UserDetails aluno = User.withUsername("aluno")
                .password("{noop}123")
                .roles("ALUNO")
                .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }

    // Configura as regras de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desabilita CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/cursos/**").authenticated() // exige login
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); // autenticação via Basic Auth

        return http.build();
    }
}
