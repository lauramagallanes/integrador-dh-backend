package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.NotBlank;

public class CategoriaEntradaDto {
    @NotBlank(message = "Debe indicar un nombre para la categoría")
    private String nombre;

    @NotBlank(message = "Debe agregar una descripcion para la categoria")
    private String descripcion;

    public CategoriaEntradaDto(){}

    public CategoriaEntradaDto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
