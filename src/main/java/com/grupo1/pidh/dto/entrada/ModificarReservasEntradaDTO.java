package com.grupo1.pidh.dto.entrada;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ModificarReservasEntradaDTO {
    @NotNull(message = "Debe ingresar una fecha disponible del producto")
    private Long disponibilidadProductoId;
    @NotBlank(message = "Debe ingresar un email de usuario")
    private String usuarioEmail;
    @NotNull(message = "Debe ingresar una cantidad de personas")
    private int cantidadPersonas;

    public ModificarReservasEntradaDTO(Long disponibilidadProductoId, String usuarioEmail, int cantidadPersonas) {
        this.disponibilidadProductoId = disponibilidadProductoId;
        this.usuarioEmail = usuarioEmail;
        this.cantidadPersonas = cantidadPersonas;
    }

    public ModificarReservasEntradaDTO() {
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

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

}
