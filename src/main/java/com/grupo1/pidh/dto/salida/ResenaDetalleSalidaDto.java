package com.grupo1.pidh.dto.salida;

import java.time.LocalDate;

public class ResenaDetalleSalidaDto {
    private String nombreUsuario;
    private int puntuacion;
    private String resena;
    private LocalDate fechaResena;

    public ResenaDetalleSalidaDto(String nombreUsuario, int puntuacion, String resena, LocalDate fechaResena) {
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
        this.resena = resena;
        this.fechaResena = fechaResena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public LocalDate getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDate fechaResena) {
        this.fechaResena = fechaResena;
    }
}
