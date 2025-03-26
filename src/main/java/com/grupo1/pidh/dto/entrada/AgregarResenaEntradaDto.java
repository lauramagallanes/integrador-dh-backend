package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AgregarResenaEntradaDto {

    @Min(1) @Max(5)
    private Integer puntuacion;
    private String resena;

    @NotNull
    private Long disponibilidadProductoId;

    public AgregarResenaEntradaDto(){}

    public AgregarResenaEntradaDto(Integer puntuacion, String resena, Long disponibilidadProductoId) {
        this.puntuacion = puntuacion;
        this.resena = resena;
        this.disponibilidadProductoId = disponibilidadProductoId;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public @NotNull Long getDisponibilidadProductoId() {
        return disponibilidadProductoId;
    }

    public void setDisponibilidadProductoId(@NotNull Long disponibilidadProductoId) {
        this.disponibilidadProductoId = disponibilidadProductoId;
    }
}
