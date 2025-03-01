package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.config.PasswordEncoderConfiguration;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.dto.salida.UsuarioSalidaDto;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.entity.Usuario;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.repository.UsuarioRepository;
import com.grupo1.pidh.service.IUsuarioService;
import com.grupo1.pidh.utils.enums.UsuarioRoles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        configureMapping();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Override
    public UserDetails registrarUsuario(RegisterRequestEntradaDto request) {
        Usuario usuario = new Usuario(request.getNombre(), request.getApellido(), request.getEmail(), passwordEncoderConfiguration.passwordEncoder().encode(request.getPassword()), UsuarioRoles.USER);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioSalidaDto> listarUsuarios() {

        List<UsuarioSalidaDto> usuarioSalidaDtos = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioSalidaDto.class))
                .toList();
        return usuarioSalidaDtos;
    }

    @Override
    public UserDetails buscarUsuarioPorMail(String email) throws UsernameNotFoundException {
        return loadUserByUsername(email);
    }

    @Override
    public void eliminatUsuario(String email) throws UsernameNotFoundException, ConflictException {

    }

    private void configureMapping() {
        modelMapper.typeMap(Usuario.class, UsuarioSalidaDto.class);
    }
}
