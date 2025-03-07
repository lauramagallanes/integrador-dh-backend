package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.ModificarUsuarioEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;
import com.grupo1.pidh.dto.entrada.ModificarUsuarioRoleEntradaDto;
import com.grupo1.pidh.dto.salida.UsuarioSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUsuarioService {
    UserDetails registrarUsuario(RegisterRequestEntradaDto request) throws ConflictException;
    List<UsuarioSalidaDto> listarUsuarios();
    UsuarioSalidaDto buscarUsuarioPorMail(String email) throws ResourceNotFoundException;
    void eliminarUsuario(String email) throws ResourceNotFoundException, ConflictException;
    UsuarioSalidaDto modificarUsuarioRole(ModificarUsuarioRoleEntradaDto modificarUsuarioRoleEntradaDto) throws ResourceNotFoundException, ConflictException;
    UsuarioSalidaDto modificarUsuario(ModificarUsuarioEntradaDto modificarUsuarioEntradaDto);
}
