package com.grupo1.pidh.service;

import com.grupo1.pidh.auth.AuthenticationResponse;
import com.grupo1.pidh.dto.entrada.LoginRequestEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;
import com.grupo1.pidh.exceptions.ConflictException;

public interface IAuthenticationService {

    AuthenticationResponse register (RegisterRequestEntradaDto request) throws ConflictException;

    AuthenticationResponse login (LoginRequestEntradaDto request);
}
