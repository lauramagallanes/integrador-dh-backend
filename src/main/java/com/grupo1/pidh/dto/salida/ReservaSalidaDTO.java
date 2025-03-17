package com.grupo1.pidh.dto.salida;


import com.grupo1.pidh.service.impl.UsuarioService;

import java.time.LocalDate;

public class ReservaSalidaDTO {
    private Long id;
    private DisponibilidadProductoSalidaDto disponibilidadProductoSalidaDto;
    private UsuarioSalidaDto usuarioSalidaDto;
    private int cantidadPersonas;

    public ReservaSalidaDTO(Long id, DisponibilidadProductoSalidaDto disponibilidadProductoSalidaDto, UsuarioSalidaDto usuarioSalidaDto, int cantidadPersonas) {
        this.id = id;
        this.disponibilidadProductoSalidaDto = disponibilidadProductoSalidaDto;
        this.usuarioSalidaDto = usuarioSalidaDto;
        this.cantidadPersonas = cantidadPersonas;
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
}
