package com.grupo1.pidh.dto.salida;

import com.grupo1.pidh.entity.Imagen;

import java.util.List;

public class ProductoSalidaDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<ImagenSalidaDto> imagenesSalidaDto;

    public ProductoSalidaDto(){

    }
    public ProductoSalidaDto(Long id, String nombre, String descripcion, List<ImagenSalidaDto> imagenesSalidaDto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenesSalidaDto = imagenesSalidaDto;
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

    public List<ImagenSalidaDto> getImagenesSalidaDto() {
        return imagenesSalidaDto;
    }

    public void setImagenesSalidaDto(List<ImagenSalidaDto> imagenesSalidaDto) {
        this.imagenesSalidaDto = imagenesSalidaDto;
    }
}
