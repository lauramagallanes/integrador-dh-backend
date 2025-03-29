package com.grupo1.pidh.entity;

import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.utils.enums.EstadoReserva;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "reseña")
    private String resena;

    @Column(name="fecha_reseña")
    private LocalDate fechaResena;
    @Column(name="codigo_confirmacion", nullable = false, unique = true)
    private String codigoConfirmacion;
    @Column(name="esta_cancelada", nullable = false)
    private boolean estaCancelada;

    public Reserva() {
    }

    public Reserva(Long id, DisponibilidadProducto disponibilidadProducto, Usuario usuario, int cantidadPersonas, String codigoConfirmacion,boolean estaCancelada) {
        this.id = id;
        this.disponibilidadProducto = disponibilidadProducto;
        this.usuario = usuario;
        this.cantidadPersonas = cantidadPersonas;
        this.codigoConfirmacion = codigoConfirmacion;
        this.estaCancelada = estaCancelada;
    }

    public Reserva(Long id, DisponibilidadProducto disponibilidadProducto, Usuario usuario, int cantidadPersonas, Integer puntuacion, String resena, LocalDate fechaResena) {
        this.id = id;
        this.disponibilidadProducto = disponibilidadProducto;
        this.usuario = usuario;
        this.cantidadPersonas = cantidadPersonas;
        this.puntuacion = puntuacion;
        this.resena = resena;
        this.fechaResena = fechaResena;
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

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public LocalDate getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDate fechaResena) {
        this.fechaResena = fechaResena;
    }

    public String getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    public void setCodigoConfirmacion(String codigoConfirmacion) {
        this.codigoConfirmacion = codigoConfirmacion;
    }

    public boolean isEstaCancelada() {
        return estaCancelada;
    }

    public void setEstaCancelada(boolean estaCancelada) {
        this.estaCancelada = estaCancelada;
    }
    public EstadoReserva getEstado(){
        EstadoReserva estadoReserva;
        LocalDateTime fechaHoraInicioEvento = disponibilidadProducto.getFechaEvento().atTime(disponibilidadProducto.getProducto().getHoraInicio());
        if (estaCancelada){
            estadoReserva = EstadoReserva.CANCELADA;
        }else if (fechaHoraInicioEvento.isBefore(LocalDateTime.now())){
            estadoReserva = EstadoReserva.FINALIZADA;
        }else{
            estadoReserva = EstadoReserva.CONFIRMADA;
        }

        return estadoReserva;
    }
}