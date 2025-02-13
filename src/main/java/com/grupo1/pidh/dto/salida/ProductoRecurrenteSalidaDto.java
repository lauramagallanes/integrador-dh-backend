package com.grupo1.pidh.dto.salida;

import com.grupo1.pidh.entity.Categoria;
import com.grupo1.pidh.entity.TipoEvento;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoTarifa;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ProductoRecurrenteSalidaDto extends ProductoSalidaDto{

    private List<DiaSemana> diasDisponible;

    public ProductoRecurrenteSalidaDto(){}

    public ProductoRecurrenteSalidaDto(Long id, String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, Set<Categoria> categorias, List<ImagenSalidaDto> imagenesSalidaDto, List<DiaSemana> diasDisponible) {
        super(id, nombre, descripcion, valorTarifa, tipoTarifa, idioma, horaInicio, horaFin, tipoEvento, categorias, imagenesSalidaDto);
        this.diasDisponible = diasDisponible;
    }

    public List<DiaSemana> getDiasDisponible() {
        return diasDisponible;
    }

    public void setDiasDisponible(List<DiaSemana> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }
}
