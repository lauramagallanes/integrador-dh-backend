package com.grupo1.pidh.dto.entrada;

import com.grupo1.pidh.entity.Imagen;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductoEntradaDto {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 100, message = "La descripción no puede tener mas de 100 caracteres")
    private String descripcion;

   //@NotNull(message = "La imagen del producto no puede ser nula")
    private List<Imagen> imagenes;

    public ProductoEntradaDto(){

    }

    public ProductoEntradaDto(String nombre, String descripcion, List<Imagen> imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
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

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }
}
