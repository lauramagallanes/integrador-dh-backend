package com.grupo1.pidh.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class FiltroProductoEntradaDto {

    private String nombre;

    @Schema(type = "string", format = "date", example = "2025-03-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;

    private String categoriaNombre;

    public FiltroProductoEntradaDto(){}

    public FiltroProductoEntradaDto(String nombre, LocalDate fechaEvento, String categoriaNombre) {
        this.nombre = nombre;
        this.fechaEvento = fechaEvento;
        this.categoriaNombre = categoriaNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
}
