package com.grupo1.pidh.service;

import com.grupo1.pidh.auth.AuthenticationResponse;
import com.grupo1.pidh.dto.entrada.LoginRequestEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;

public interface IAuthenticationService {

    AuthenticationResponse register (RegisterRequestEntradaDto request);

    AuthenticationResponse login (LoginRequestEntradaDto request);
}
