package com.grupo1.pidh.utils.enums;

public enum PoliticaPagos {
    RESERVA_Y_PAGO_POSTERIOR("Disfruta de mayor flexibilidad en tus planes reservando ahora y pagando hasta dos días antes del inicio de la experiencia. La fecha de vencimiento del pago dependerá del método seleccionado, pero también puedes pagarlo en cualquier momento antes del plazo límite.Para garantizar tu reserva, deberás ingresar una tarjeta de pago al momento de la reserva. El cobro se realizará automáticamente máximo 48 horas antes de la actividad, a menos que realices el pago manualmente antes."),
    PAGO_INMEDIATO("Esta actividad requiere pago inmediato al momento de la reserva. Solo se confirmará tu participación una vez que el pago se haya procesado con éxito.Asegura tu lugar ahora y recibe la confirmación de tu reserva al instante. No se permite el pago posterior."),
    PAGO_PARCIAL("Realiza un pago parcial para confirmar tu reserva. El resto se paga el día de la experiencia.Si cancelas con al menos 7 días de antelación, recibirás un reembolso completo.");


    private final String descripcion;

    PoliticaPagos(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
