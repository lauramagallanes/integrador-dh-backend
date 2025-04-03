package com.grupo1.pidh.dto.salida;

public class FavoritoSalidaDTO {

    private Long id;
    private ProductoSalidaDto producto;

    public FavoritoSalidaDTO() {
    }

    public FavoritoSalidaDTO(Long id, ProductoSalidaDto producto) {
        this.id = id;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductoSalidaDto getProducto() {
        return producto;
    }

    public void setProducto(ProductoSalidaDto producto) {
        this.producto = producto;
    }
}