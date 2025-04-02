package com.grupo1.pidh.dto.entrada;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class CategoriaEntradaDto {
    @NotBlank(message = "Debe indicar un nombre para la categoría")
    private String nombre;

    @NotBlank(message = "Debe agregar una descripcion para la categoria")
    private String descripcion;

    private MultipartFile imagenCategoria;
    private boolean activo;

    public CategoriaEntradaDto(){}

    public CategoriaEntradaDto(String nombre, String descripcion, MultipartFile imagenCategoria, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenCategoria = imagenCategoria;
        this.activo = activo;
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

    public MultipartFile getImagenCategoria() {
        return imagenCategoria;
    }

    public void setImagenCategoria(MultipartFile imagenCategoria) {
        this.imagenCategoria = imagenCategoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
