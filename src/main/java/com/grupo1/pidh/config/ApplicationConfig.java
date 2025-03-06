package com.grupo1.pidh.config;

import com.grupo1.pidh.repository.UsuarioRepository;
import com.grupo1.pidh.service.impl.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ApplicationConfig {
    private final UsuarioService usuarioService;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    public ApplicationConfig(UsuarioService usuarioService, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.usuarioService = usuarioService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(passwordEncoderConfiguration.passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider()));
    }

}
