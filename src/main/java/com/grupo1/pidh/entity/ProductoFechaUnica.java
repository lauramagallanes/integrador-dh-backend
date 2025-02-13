package com.grupo1.pidh.entity;

import com.grupo1.pidh.utils.enums.TipoTarifa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PRODUCTOS_FECHA_UNICA")
@PrimaryKeyJoinColumn(name = "producto_id") //Indica que el id de Producto es la clave primaria
public class ProductoFechaUnica extends Producto {


    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;



    public ProductoFechaUnica() {}

    public ProductoFechaUnica(Long id, String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, Set<Categoria> categorias, TipoEvento tipoEvento, List<Imagen> imagenes, LocalDate fechaEvento) {
        super(id, nombre, descripcion, valorTarifa, tipoTarifa, idioma, horaInicio, horaFin, categorias, tipoEvento, imagenes);
        this.fechaEvento = fechaEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}
