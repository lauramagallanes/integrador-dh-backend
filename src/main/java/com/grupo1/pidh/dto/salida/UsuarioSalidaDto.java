package com.grupo1.pidh.dto.salida;

import com.grupo1.pidh.utils.enums.UsuarioRoles;


public class UsuarioSalidaDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private UsuarioRoles usuarioRoles;

    public UsuarioSalidaDto() {
    }

    public UsuarioSalidaDto(Long id, String nombre, String apellido, String email, UsuarioRoles usuarioRoles) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.usuarioRoles = usuarioRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioRoles getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(UsuarioRoles usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }
}
