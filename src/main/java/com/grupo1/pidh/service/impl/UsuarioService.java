package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.config.PasswordEncoderConfiguration;
import com.grupo1.pidh.dto.entrada.ModificarUsuarioEntradaDto;
import com.grupo1.pidh.dto.entrada.ModificarUsuarioRoleEntradaDto;
import com.grupo1.pidh.dto.entrada.RegisterRequestEntradaDto;
import com.grupo1.pidh.dto.salida.UsuarioSalidaDto;
import com.grupo1.pidh.entity.Usuario;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.UsuarioRepository;
import com.grupo1.pidh.service.IUsuarioService;
import com.grupo1.pidh.utils.enums.UsuarioRoles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public UserDetails registrarUsuario(RegisterRequestEntradaDto request) throws ConflictException {
        Usuario usuarioRegistrado = null;
        try {
            Usuario usuario = new Usuario(request.getNombre(), request.getApellido(), request.getEmail(), passwordEncoderConfiguration.passwordEncoder().encode(request.getPassword()), UsuarioRoles.USER, false);
            usuarioRegistrado = usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException e){
            throw new ConflictException("El email ingresado ya está registrado");
        }
        return usuarioRegistrado;
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
    public UsuarioSalidaDto buscarUsuarioPorMail(String email) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioSalidaDto.class);
    }

    @Override
    public void eliminarUsuario(String email) throws UsernameNotFoundException, ConflictException {
        UsuarioSalidaDto usuarioSalidaDto = buscarUsuarioPorMail(email);
        if (usuarioSalidaDto != null) {
            try {
                usuarioRepository.deleteById(usuarioSalidaDto.getId());
            }catch (DataIntegrityViolationException e){
                throw new ConflictException("El usuario seleccionado no puede ser eliminado ya que tiene información relacionada");
            }catch (Exception e){
                throw new RuntimeException("Error eliminando el usuario");
            }
        }
    }

    @Override
    public UsuarioSalidaDto modificarUsuarioRole(ModificarUsuarioRoleEntradaDto modificarUsuarioRoleEntradaDto) throws ResourceNotFoundException, ConflictException {
        Usuario usuario = usuarioRepository.findByEmail(modificarUsuarioRoleEntradaDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (usuario.getEsSuperAdmin()){
            throw new ConflictException("No se puede cambiar el rol a un super admin");
        }else {
            usuario.setUsuarioRoles(modificarUsuarioRoleEntradaDto.getRol());
        }
        return modelMapper.map(usuarioRepository.save(usuario), UsuarioSalidaDto.class);
    }

    @Override
    public UsuarioSalidaDto modificarUsuario(ModificarUsuarioEntradaDto modificarUsuarioEntradaDto) {
        Usuario usuario = usuarioRepository.findByEmail(modificarUsuarioEntradaDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        usuario.setNombre(modificarUsuarioEntradaDto.getNombre());
        usuario.setApellido(modificarUsuarioEntradaDto.getApellido());

        return modelMapper.map(usuarioRepository.save(usuario), UsuarioSalidaDto.class);
    }

    private void configureMapping() {
        modelMapper.typeMap(Usuario.class, UsuarioSalidaDto.class);
    }
}
