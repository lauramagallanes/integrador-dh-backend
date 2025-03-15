package com.grupo1.pidh.utils.enums;

public enum PoliticaCancelacion {
    FLEXIBLE("Permite cancelaciones hasta 24 horas antes sin costo"),
    MODERADA("Permite cancelaciones hasta 48 horas antes con reembolso parcial"),
    ESTRICTA("No permite cancelaciones, salvo casos de fuerza mayor"),
    NO_REEMBOLSABLE("No se aceptan cancelaciones bajo ninguna circunstancia");

    private final String descripcion;
    PoliticaCancelacion(String descripcion){
        this.descripcion=descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
