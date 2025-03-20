package com.grupo1.pidh.dto.entrada;

import javax.validation.constraints.NotNull;

public class FavoritoEntradaDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    public FavoritoEntradaDTO() {
    }

    public FavoritoEntradaDTO(Long productoId) {
        this.productoId = productoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}

