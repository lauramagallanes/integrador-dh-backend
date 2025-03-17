package com.grupo1.pidh.utils.enums;

public enum PoliticaPagos {
    PAGO_TOTAL_ANTICIPADO("Se paga el total antes de la actividad"),
    PAGO_PARCIAL_ANTICIPADO("Se paga un porcentaje antes y el resto al inicio"),
    PAGO_AL_INICIO("Se paga al comenzar la actividad"),
    PAGO_AL_FINAL("Se paga al finalizar la actividad");

    private final String descripcion;

    PoliticaPagos(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
