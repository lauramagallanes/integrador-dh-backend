package com.grupo1.pidh.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DISPONIBILIDAD_PRODUCTO")
public class DisponibilidadProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference
    private Producto producto;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "cupos_totales", nullable = false)
    private int cuposTotales;

    @Column(name = "cupos_reservados", nullable = false)
    private int cuposReservados;

    @OneToMany(mappedBy = "disponibilidadProducto", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reserva> reservas;



    public DisponibilidadProducto(){}

    public DisponibilidadProducto(Long id, Producto producto, LocalDate fechaEvento, int cuposTotales, int cuposReservados, List<Reserva> reservas) {
        this.id = id;
        this.producto = producto;
        this.fechaEvento = fechaEvento;
        this.cuposTotales = cuposTotales;
        this.cuposReservados = cuposReservados;
        this.reservas = reservas;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }


    public int getCuposTotales() {
        return cuposTotales;
    }

    public void setCuposTotales(int cuposTotales) {
        this.cuposTotales = cuposTotales;
    }

    public int getCuposReservados() {
        return cuposReservados;
    }

    public void setCuposReservados(int cuposReservados) {
        this.cuposReservados = cuposReservados;
    }

    public int getCuposDisponibles(){
        return (cuposTotales - cuposReservados);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
