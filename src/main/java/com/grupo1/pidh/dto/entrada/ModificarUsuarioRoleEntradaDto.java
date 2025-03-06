package com.grupo1.pidh.dto.entrada;

import com.grupo1.pidh.utils.enums.UsuarioRoles;

import javax.validation.constraints.*;

public class ModificarUsuarioRoleEntradaDto {

    @NotBlank(message = "El email no puede estar vacio")
    @Size(max = 320, message = "El mail debe tener hasta 320 caracteres")
    @Email(message = "Debe ser un email válido")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El email no cumple con el formato válido"
    )
    private String email;

    @NotNull(message = "Debe seleccionar un rol")
    private UsuarioRoles rol;

    public ModificarUsuarioRoleEntradaDto(){}
    public ModificarUsuarioRoleEntradaDto(String email, UsuarioRoles rol) {
        this.email = email;
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioRoles getRol() {
        return rol;
    }

    public void setRol(UsuarioRoles rol) {
        this.rol = rol;
    }
}
