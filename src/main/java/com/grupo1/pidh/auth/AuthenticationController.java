package com.grupo1.pidh.auth;

import com.grupo1.pidh.dto.entrada.LoginRequestEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name= "GoBook API", description = "Endpoints de autenteicación de Usuarios")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en la BD y devuelve un token")
    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequestEntradaDto request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @Operation(summary = "Iniciar sesion con un usuario", description = "inicia sesion con un usuario en la BD y devuelve un token")
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestEntradaDto request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
