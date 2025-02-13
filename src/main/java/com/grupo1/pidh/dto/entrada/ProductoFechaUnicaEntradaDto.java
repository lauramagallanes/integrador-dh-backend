package com.grupo1.pidh.dto.entrada;

import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.utils.enums.TipoTarifa;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ProductoFechaUnicaEntradaDto extends ProductoEntradaDto {



    @NotNull(message = "Debe indicar una fecha para el evento")
    private LocalDate fechaEvento;

    public ProductoFechaUnicaEntradaDto(){}

    public ProductoFechaUnicaEntradaDto(String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, String tipoEventoNombre, Set<String> categoriasNombres, List<Imagen> imagenes, LocalDate fechaEvento) {
        super(nombre, descripcion, valorTarifa, tipoTarifa, idioma, horaInicio, horaFin, tipoEventoNombre, categoriasNombres, imagenes);
        this.fechaEvento = fechaEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}
