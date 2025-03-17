package com.grupo1.pidh.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RESERVAS")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "disponibilidad_producto_id", nullable = false)
    private DisponibilidadProducto disponibilidadProducto;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @Column(name = "cantidad_personas", nullable = false)
    private int cantidadPersonas;

    public Reserva() {
    }

    public Reserva(Long id, DisponibilidadProducto disponibilidadProducto, Usuario usuario, int cantidadPersonas) {
        this.id = id;
        this.disponibilidadProducto = disponibilidadProducto;
        this.usuario = usuario;
        this.cantidadPersonas = cantidadPersonas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisponibilidadProducto getDisponibilidadProducto() {
        return disponibilidadProducto;
    }

    public void setDisponibilidadProducto(DisponibilidadProducto disponibilidadProducto) {
        this.disponibilidadProducto = disponibilidadProducto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }
}