package com.grupo1.pidh.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grupo1.pidh.utils.converter.DiaSemanaConverter;
import com.grupo1.pidh.utils.enums.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="PRODUCTOS")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true, name = "nombre")
    private String nombre;

    @Column(length = 100, nullable = false, name = "descripcion")
    private String descripcion;

    @Column (name = "valor_tarifa", nullable = false)
    private Double valorTarifa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tarifa", nullable = false)
    private TipoTarifa tipoTarifa;

    @Column(name = "idioma", nullable = false)
    private String idioma;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;


    //@Column(name = "fecha_evento")
    //private LocalDate fechaEvento;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DisponibilidadProducto> disponibilidad;

    @Convert(converter = DiaSemanaConverter.class) //uso el converter que creé
    @Column(name = "dias_disponible")
    private List<DiaSemana> diasDisponible;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "producto_categorias",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "producto_caracteristicas",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )
    private Set<Caracteristica> caracteristicas;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductoImagen> productoImagenes;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "direccion")
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "politica_cancelacion")
    private PoliticaCancelacion politicaCancelacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "politica_pagos")
    private PoliticaPagos politicaPagos;

    public Producto(Long id, String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, List<DisponibilidadProducto> disponibilidad, List<DiaSemana> diasDisponible, Set<Categoria> categorias, Set<Caracteristica> caracteristicas, List<ProductoImagen> productoImagenes, String pais, String ciudad, String direccion, PoliticaCancelacion politicaCancelacion, PoliticaPagos politicaPagos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorTarifa = valorTarifa;
        this.tipoTarifa = tipoTarifa;
        this.idioma = idioma;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoEvento = tipoEvento;
        this.disponibilidad = disponibilidad;
        this.diasDisponible = diasDisponible;
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
        this.productoImagenes = productoImagenes;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.politicaCancelacion = politicaCancelacion;
        this.politicaPagos = politicaPagos;
    }

    public Producto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValorTarifa() {
        return valorTarifa;
    }

    public void setValorTarifa(Double valorTarifa) {
        this.valorTarifa = valorTarifa;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public List<DisponibilidadProducto> getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(List<DisponibilidadProducto> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<DiaSemana> getDiasDisponible() {
        return diasDisponible;
    }

    public void setDiasDisponible(List<DiaSemana> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }


    public List<ProductoImagen> getProductoImagenes() {
        return productoImagenes;
    }

    public void setProductoImagenes(List<ProductoImagen> productoImagenes) {
        this.productoImagenes = productoImagenes;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public PoliticaCancelacion getPoliticaCancelacion() {
        return politicaCancelacion;
    }

    public void setPoliticaCancelacion(PoliticaCancelacion politicaCancelacion) {
        this.politicaCancelacion = politicaCancelacion;
    }

    public PoliticaPagos getPoliticaPagos() {
        return politicaPagos;
    }

    public void setPoliticaPagos(PoliticaPagos politicaPagos) {
        this.politicaPagos = politicaPagos;
    }
}
