package com.grupo1.pidh.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoEvento;
import com.grupo1.pidh.utils.enums.TipoTarifa;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @NotNull(message = "Debe seleccionar un tipo de tarifa")
    private TipoTarifa tipoTarifa;

    @NotBlank(message = "Debe elegir un idioma")
    private String idioma;

    @Schema(type = "string", example = "14:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @NotNull(message = "Debe seleccionar una hora de inicio")
    private LocalTime horaInicio;

    @Schema(type = "string", example = "14:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @NotNull(message = "Debe seleccionar una hora de finalización")
    private LocalTime horaFin;

    @NotNull(message = "Debe seleccionar un tipo de evento")
    private TipoEvento tipoEvento;

    private List<DiaSemana> diasDisponible;

    @Schema(type = "string", format = "date", example = "2025-02-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;


    //@NotEmpty(message = "Debe seleccionar al menos una categoría")
    private Set<Long> categoriasIds;

    private Set<Long> caracteristicasIds;

    private List<ProductoImagenEntradaDto> productoImagenes;

    public ProductoEntradaDto(){

    }

    public ProductoEntradaDto(String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, List<DiaSemana> diasDisponible, LocalDate fechaEvento, Set<Long> categoriasIds, Set<Long> caracteristicasIds, List<ProductoImagenEntradaDto> productoImagenes) {
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
        this.categoriasIds = categoriasIds;
        this.caracteristicasIds = caracteristicasIds;
        this.productoImagenes = productoImagenes;
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

    public Set<Long> getCategoriasIds() {
        return categoriasIds;
    }

    public void setCategoriasIds(Set<Long> categoriasIds) {
        this.categoriasIds = categoriasIds;
    }

    public List<ProductoImagenEntradaDto> getProductoImagenes() {
        return productoImagenes;
    }

    public void setProductoImagenes(List<ProductoImagenEntradaDto> productoImagenes) {
        this.productoImagenes = productoImagenes;
    }

    public Set<Long> getCaracteristicasIds() {
        return caracteristicasIds;
    }

    public void setCaracteristicasIds(Set<Long> caracteristicasIds) {
        this.caracteristicasIds = caracteristicasIds;
    }
}
