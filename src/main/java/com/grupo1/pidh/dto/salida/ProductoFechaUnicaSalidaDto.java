package com.grupo1.pidh.dto.salida;

import com.grupo1.pidh.entity.Categoria;
import com.grupo1.pidh.entity.TipoEvento;
import com.grupo1.pidh.utils.enums.TipoTarifa;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ProductoFechaUnicaSalidaDto extends ProductoSalidaDto{
    private LocalDate fechaEvento;

    public ProductoFechaUnicaSalidaDto(){}

    public ProductoFechaUnicaSalidaDto(Long id, String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, Set<Categoria> categorias, List<ImagenSalidaDto> imagenesSalidaDto, LocalDate fechaEvento) {
        super(id, nombre, descripcion, valorTarifa, tipoTarifa, idioma, horaInicio, horaFin, tipoEvento, categorias, imagenesSalidaDto);
        this.fechaEvento = fechaEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}
