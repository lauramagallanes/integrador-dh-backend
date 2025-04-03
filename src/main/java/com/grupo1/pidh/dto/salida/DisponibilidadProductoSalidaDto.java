package com.grupo1.pidh.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DisponibilidadProductoSalidaDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;

    private int cuposTotales;
    private int cuposReservados;
    private Long productoId;
    private int cuposDisponibles;

    public DisponibilidadProductoSalidaDto(){}

    public DisponibilidadProductoSalidaDto(Long id, LocalDate fechaEvento, int cuposTotales, int cuposReservados, Long productoId, int cuposDisponibles) {
        this.id = id;
        this.fechaEvento = fechaEvento;
        this.cuposTotales = cuposTotales;
        this.cuposReservados = cuposReservados;
        this.productoId = productoId;
        this.cuposDisponibles = cuposDisponibles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }
}
