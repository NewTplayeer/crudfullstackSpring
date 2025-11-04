package com.faculdade.api.service;

import com.faculdade.api.model.Usuario;
import com.faculdade.api.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .disabled(!u.getEnabled())
                .authorities(u.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getNome()))
                        .collect(Collectors.toList()))
                .build();
    }
}
