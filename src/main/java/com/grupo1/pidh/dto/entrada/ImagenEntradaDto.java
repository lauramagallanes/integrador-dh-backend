package com.grupo1.pidh.dto.entrada;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class ImagenEntradaDto {

    @NotBlank(message = "El link a la imagen no puede estar vacío")
    private String rutaImagen;
    private MultipartFile file;
    public ImagenEntradaDto(){

    }
    public ImagenEntradaDto(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
