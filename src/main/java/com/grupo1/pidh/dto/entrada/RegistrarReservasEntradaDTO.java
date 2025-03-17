package com.grupo1.pidh.dto.entrada;


import com.grupo1.pidh.utils.enums.TipoTarifa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class RegistrarReservasEntradaDTO {
    @NotNull(message = "Debe ingresar una fecha disponible del producto")
    private Long disponibilidadProductoId;
    @NotBlank(message = "Debe ingresar un email de usuario")
    private String usuarioEmail;
    @NotNull(message = "Debe ingresar una tarifa")
    private TipoTarifa tipoTarifa;
    @Positive(message = "Debe ingresar la cantidad de reservas que quiere realizar")
    private int cantidadReservas;

    public RegistrarReservasEntradaDTO(Long disponibilidadProductoId, String usuarioEmail, TipoTarifa tipoTarifa, int cantidadReservas) {
        this.disponibilidadProductoId = disponibilidadProductoId;
        this.usuarioEmail = usuarioEmail;
        this.tipoTarifa = tipoTarifa;
        this.cantidadReservas = cantidadReservas;
    }

    public RegistrarReservasEntradaDTO() {
    }

    public Long getDisponibilidadProductoId() {
        return disponibilidadProductoId;
    }

    public void setDisponibilidadProductoId(Long disponibilidadProductoId) {
        this.disponibilidadProductoId = disponibilidadProductoId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }
}
