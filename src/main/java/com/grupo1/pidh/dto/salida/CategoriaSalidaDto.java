package com.grupo1.pidh.dto.salida;

public class CategoriaSalidaDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenCategoriaUrl;

    public CategoriaSalidaDto(){}

    public CategoriaSalidaDto(Long id, String nombre, String descripcion, String imagenCategoriaUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenCategoriaUrl = imagenCategoriaUrl;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenCategoriaUrl() {
        return imagenCategoriaUrl;
    }

    public void setImagenCategoriaUrl(String imagenCategoriaUrl) {
        this.imagenCategoriaUrl = imagenCategoriaUrl;
    }
}
