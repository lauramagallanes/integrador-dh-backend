package com.grupo1.pidh.dto.salida;

import com.grupo1.pidh.entity.Producto;

public class ImagenSalidaDto {

    private Long id;
    private String rutaImagen;

    public ImagenSalidaDto(){

    }

    public ImagenSalidaDto(Long id, String rutaImagen) {
        this.id = id;
        this.rutaImagen = rutaImagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
