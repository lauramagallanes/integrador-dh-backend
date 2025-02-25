package com.grupo1.pidh.dto.salida;

public class ProductoImagenSalidaDto {

    private Long id;
    private String rutaImagen;

    public ProductoImagenSalidaDto(){

    }

    public ProductoImagenSalidaDto(Long id, String rutaImagen) {
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
