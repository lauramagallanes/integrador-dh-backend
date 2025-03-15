package com.grupo1.pidh.utils.enums;

public enum PoliticaCancelacion {
    REEMBOLSO_7_DIAS("Para obtener un reembolso completo, debes cancelar tu reserva al menos 7 días antes de la fecha de la experiencia. Si cancelas con menos de 7 días de antelación, no se reembolsará el importe abonado.La cancelación se basa en la hora local del destino donde se realiza la experiencia."),
    REEMBOLSO_24_HORAS("Para recibir un reembolso completo, debes cancelar la experiencia al menos 24 horas antes de su inicio. Si la cancelación se realiza con menos tiempo, no se realizará ningún reembolso. La hora límite se basa en la hora local del destino de la experiencia. Además, si las condiciones meteorológicas no permiten llevar a cabo la actividad, se te ofrecerá una nueva fecha o el reembolso total del importe pagado."),
    NO_REEMBOLSABLE("Estas experiencias no son reembolsables y no se pueden cambiar por ningún motivo. Si cancelas o pides una modificación, no se reembolsará el importe abonado.");


    private final String descripcion;
    PoliticaCancelacion(String descripcion){
        this.descripcion=descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
