package com.grupo1.pidh.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DisponibilidadProductoSalidaDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;

    private int cuposTotales;
    private int cuposReservados;

    public DisponibilidadProductoSalidaDto(){}

    public DisponibilidadProductoSalidaDto(LocalDate fechaEvento, int cuposTotales, int cuposReservados) {
        this.fechaEvento = fechaEvento;
        this.cuposTotales = cuposTotales;
        this.cuposReservados = cuposReservados;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public int getCuposTotales() {
        return cuposTotales;
    }

    public void setCuposTotales(int cuposTotales) {
        this.cuposTotales = cuposTotales;
    }

    public int getCuposReservados() {
        return cuposReservados;
    }

    public void setCuposReservados(int cuposReservados) {
        this.cuposReservados = cuposReservados;
    }
}
