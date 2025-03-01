package com.grupo1.pidh.auth;

import com.grupo1.pidh.dto.entrada.LoginRequestEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;
import com.grupo1.pidh.service.impl.JwtService;
import com.grupo1.pidh.service.impl.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UsuarioService usuarioService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register (RegisterRequestEntradaDto request){
        UserDetails usuario = usuarioService.registrarUsuario(request);
        String token = jwtService.generateToken(usuario);
        return (new AuthenticationResponse(token));
    }

    public AuthenticationResponse login (LoginRequestEntradaDto request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails usuario = usuarioService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(usuario);
        return (new AuthenticationResponse(token));
    }
}
