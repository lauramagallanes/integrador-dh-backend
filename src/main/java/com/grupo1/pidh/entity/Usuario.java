package com.grupo1.pidh.entity;

import com.grupo1.pidh.utils.enums.UsuarioRoles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="USUARIOS")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false, name = "nombre")
    private String nombre;
    @Column(length = 20, nullable = false, name = "apellido")
    private String apellido;
    @Column(length = 320, nullable = false, unique = true, name = "email")
    private String email;
    @Column(length = 60, nullable = false, name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    private UsuarioRoles usuarioRoles;
    @Column(nullable = false, name = "EsSuperAdmin")
    private boolean esSuperAdmin;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String password, UsuarioRoles usuarioRoles, boolean esSuperAdmin) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.usuarioRoles = usuarioRoles;
        this.esSuperAdmin = esSuperAdmin;
    }

    public Long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioRoles getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(UsuarioRoles usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }

    public boolean getEsSuperAdmin(){
        return esSuperAdmin;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuarioRoles.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
