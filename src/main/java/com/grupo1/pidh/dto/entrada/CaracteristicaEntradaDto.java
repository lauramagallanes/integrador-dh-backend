package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.NotBlank;

public class CaracteristicaEntradaDto {

    @NotBlank(message = "Debe indicar un nombre para la caracteristica")
    private String nombre;

    @NotBlank(message = "Debe seleccionar un icono para esta caracteristica")
    private String icono;

    public CaracteristicaEntradaDto(){}

    public CaracteristicaEntradaDto(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
