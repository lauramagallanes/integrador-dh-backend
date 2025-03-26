package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AgregarResenaEntradaDto {

    @Min(1) @Max(5)
    private Integer puntuacion;
    private String resena;

    @NotNull
    private Long reservaId;

    public AgregarResenaEntradaDto(){}

    public AgregarResenaEntradaDto(Integer puntuacion, String resena, Long reservaId) {
        this.puntuacion = puntuacion;
        this.resena = resena;
        this.reservaId = reservaId;
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

    public @NotNull Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(@NotNull Long reservaId) {
        this.reservaId = reservaId;
    }
}
