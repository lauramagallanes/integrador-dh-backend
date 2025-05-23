package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterRequestEntradaDto {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 20, message = "El nombre debe tener hasta 20 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(max = 20, message = "El apellido debe tener hasta 20 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacio")
    @Size(max = 320, message = "El mail debe tener hasta 320 caracteres")
    @Email(message = "Debe ser un email válido")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El email no cumple con el formato válido"
    )
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).*$",
            message = "La contraseña debe incluir al menos una minúscula, una mayúscula, un número y un carácter especial"
    )
    private String password;

    public RegisterRequestEntradaDto(){}
    public RegisterRequestEntradaDto(String nombre, String apellido, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
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
}
