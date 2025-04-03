package com.grupo1.pidh.dto.entrada;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RegistrarReservasEntradaDTO {
    @NotNull(message = "Debe ingresar una fecha disponible del producto")
    private Long disponibilidadProductoId;
    @NotBlank(message = "Debe ingresar un email de usuario")
    private String usuarioEmail;
    @Positive(message = "Debe ingresar la cantidad de reservas que quiere realizar")
    private int cantidadPersonas;

    public RegistrarReservasEntradaDTO(Long disponibilidadProductoId, String usuarioEmail, int cantidadpersonas) {
        this.disponibilidadProductoId = disponibilidadProductoId;
        this.usuarioEmail = usuarioEmail;
        this.cantidadPersonas = cantidadpersonas;
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


    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }
}
