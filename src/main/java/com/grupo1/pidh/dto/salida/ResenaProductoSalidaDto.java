package com.grupo1.pidh.dto.salida;

import java.util.List;

public class ResenaProductoSalidaDto {
    private Long productoId;
    private double promedioPuntuacion;
    private int totalResenas;
    private List<ResenaDetalleSalidaDto> resenas;

    public ResenaProductoSalidaDto(Long productoId, double promedioPuntuacion, int totalResenas, List<ResenaDetalleSalidaDto> resenas) {
        this.productoId = productoId;
        this.promedioPuntuacion = promedioPuntuacion;
        this.totalResenas = totalResenas;
        this.resenas = resenas;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public double getPromedioPuntuacion() {
        return promedioPuntuacion;
    }

    public void setPromedioPuntuacion(double promedioPuntuacion) {
        this.promedioPuntuacion = promedioPuntuacion;
    }

    public int getTotalResenas() {
        return totalResenas;
    }

    public void setTotalResenas(int totalResenas) {
        this.totalResenas = totalResenas;
    }

    public List<ResenaDetalleSalidaDto> getResenas() {
        return resenas;
    }

    public void setResenas(List<ResenaDetalleSalidaDto> resenas) {
        this.resenas = resenas;
    }
}
