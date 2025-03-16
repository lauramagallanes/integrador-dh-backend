package com.grupo1.pidh.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DisponibilidadProductoSalidaDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;

    private int cuposTotales;
    private int cuposReservados;
    private Long productoId;

    public DisponibilidadProductoSalidaDto(){}

    public DisponibilidadProductoSalidaDto(LocalDate fechaEvento, int cuposTotales, int cuposReservados, Long productoId) {
        this.fechaEvento = fechaEvento;
        this.cuposTotales = cuposTotales;
        this.cuposReservados = cuposReservados;
        this.productoId = productoId;
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

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}
