package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ModificarUsuarioEntradaDto {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 20, message = "El nombre debe tener hasta 20 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(max = 20, message = "El apellido debe tener hasta 20 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacio")
    private String email;

    public ModificarUsuarioEntradaDto(){}
    public ModificarUsuarioEntradaDto(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
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
}
