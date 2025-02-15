package com.grupo1.pidh.dto.entrada;

import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoEvento;
import com.grupo1.pidh.utils.enums.TipoTarifa;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ProductoEntradaDto {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 100, message = "La descripción no puede tener mas de 100 caracteres")
    private String descripcion;

    @NotNull(message = "El valor de la tarifa es obligatorio")
    @Positive(message = "El valor de la tarifa debe ser mayor a 0")
    private Double valorTarifa;

    @NotBlank(message = "Debe seleccionar un tipo de tarifa")
    private TipoTarifa tipoTarifa;

    @NotBlank(message = "Debe elegir un idioma")
    private String idioma;

    @NotNull(message = "Debe seleccionar una hora de inicio")
    private LocalTime horaInicio;

    @NotNull(message = "Debe seleccionar una hora de finalización")
    private LocalTime horaFin;

    @NotBlank(message = "Debe seleccionar un tipo de evento")
    private TipoEvento tipoEvento;

    private List<DiaSemana> diasDisponible;

    private LocalDate fechaEvento;


    //@NotEmpty(message = "Debe seleccionar al menos una categoría")
    private Set<String> categoriasNombres;

    private List<ImagenEntradaDto> imagenes;

    public ProductoEntradaDto(){

    }

    public ProductoEntradaDto(String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, List<DiaSemana> diasDisponible, LocalDate fechaEvento, Set<String> categoriasNombres, List<ImagenEntradaDto> imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorTarifa = valorTarifa;
        this.tipoTarifa = tipoTarifa;
        this.idioma = idioma;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoEvento = tipoEvento;
        this.diasDisponible = diasDisponible;
        this.fechaEvento = fechaEvento;
        this.categoriasNombres = categoriasNombres;
        this.imagenes = imagenes;
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

    public List<DiaSemana> getDiasDisponible() {
        return diasDisponible;
    }

    public void setDiasDisponible(List<DiaSemana> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Set<String> getCategoriasNombres() {
        return categoriasNombres;
    }

    public void setCategoriasNombres(Set<String> categoriasNombres) {
        this.categoriasNombres = categoriasNombres;
    }

    public List<ImagenEntradaDto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenEntradaDto> imagenes) {
        this.imagenes = imagenes;
    }
}
