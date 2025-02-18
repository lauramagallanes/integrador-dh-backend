package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.NotBlank;

public class CategoriaEntradaDto {
    @NotBlank(message = "Debe indicar un nombre para la categoría")
    private String nombre;

    public CategoriaEntradaDto(){}

    public CategoriaEntradaDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
