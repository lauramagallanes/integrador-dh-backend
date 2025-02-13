package com.grupo1.pidh.entity;

import com.grupo1.pidh.utils.converter.EnumListConverter;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoTarifa;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PRODUCTOS_RECURRENTE")
@PrimaryKeyJoinColumn(name = "producto_id")
public class ProductoRecurrente extends Producto {

    @Convert(converter = EnumListConverter.class) //uso el converter que creé
    @Column(name = "dias_disponible")
    private List<DiaSemana> diasDisponible;

    public ProductoRecurrente(){}

    public ProductoRecurrente(Long id, String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, Set<Categoria> categorias, TipoEvento tipoEvento, List<Imagen> imagenes, List<DiaSemana> diasDisponible) {
        super(id, nombre, descripcion, valorTarifa, tipoTarifa, idioma, horaInicio, horaFin, categorias, tipoEvento, imagenes);
        this.diasDisponible = diasDisponible;
    }

    public List<DiaSemana> getDiasDisponible() {
        return diasDisponible;
    }

    public void setDiasDisponible(List<DiaSemana> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }
}
