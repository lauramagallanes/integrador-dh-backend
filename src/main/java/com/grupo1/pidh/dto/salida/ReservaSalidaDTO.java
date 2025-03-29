package com.grupo1.pidh.dto.salida;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.grupo1.pidh.service.impl.UsuarioService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class ReservaSalidaDTO {
    private Long id;
    private DisponibilidadProductoSalidaDto disponibilidadProductoSalidaDto;
    private UsuarioSalidaDto usuarioSalidaDto;
    private int cantidadPersonas;
    private Integer puntuacion;
    private String resena;
    private String codigoConfirmacion;

    @Schema(type = "string", example = "2025-06-17")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaResena;

    public ReservaSalidaDTO(Long id, DisponibilidadProductoSalidaDto disponibilidadProductoSalidaDto, UsuarioSalidaDto usuarioSalidaDto, int cantidadPersonas, Integer puntuacion, String resena, LocalDate fechaResena, String codigoConfirmacion) {
        this.id = id;
        this.disponibilidadProductoSalidaDto = disponibilidadProductoSalidaDto;
        this.usuarioSalidaDto = usuarioSalidaDto;
        this.cantidadPersonas = cantidadPersonas;
        this.puntuacion = puntuacion;
        this.resena = resena;
        this.fechaResena = fechaResena;
        this.codigoConfirmacion = codigoConfirmacion;
    }

    public ReservaSalidaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisponibilidadProductoSalidaDto getDisponibilidadProductoSalidaDto() {
        return disponibilidadProductoSalidaDto;
    }

    public void setDisponibilidadProductoSalidaDto(DisponibilidadProductoSalidaDto disponibilidadProductoSalidaDto) {
        this.disponibilidadProductoSalidaDto = disponibilidadProductoSalidaDto;
    }

    public UsuarioSalidaDto getUsuarioSalidaDTO() {
        return usuarioSalidaDto;
    }

    public void setUsuarioSalidaDto(UsuarioSalidaDto usuarioSalidaDto) {
        this.usuarioSalidaDto = usuarioSalidaDto;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public UsuarioSalidaDto getUsuarioSalidaDto() {
        return usuarioSalidaDto;
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

    public LocalDate getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDate fechaResena) {
        this.fechaResena = fechaResena;
    }

    public String getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    public void setCodigoConfirmacion(String codigoConfirmacion) {
        this.codigoConfirmacion = codigoConfirmacion;
    }
}
