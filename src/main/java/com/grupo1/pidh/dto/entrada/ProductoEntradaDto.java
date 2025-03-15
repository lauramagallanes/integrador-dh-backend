package com.grupo1.pidh.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grupo1.pidh.utils.enums.*;
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

    @Schema(type = "string", format = "date", example = "2025-02-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFinEvento;


    //@NotEmpty(message = "Debe seleccionar al menos una categoría")
    private Set<Long> categoriasIds;

    private Set<Long> caracteristicasIds;

    private List<ProductoImagenEntradaDto> productoImagenes;

    @NotBlank(message = "Debe ingresar un Pais")
    private String pais;

    @NotBlank(message = "Debe ingresar una ciudad")
    private String ciudad;

    @NotBlank(message = "Debe ingresar una dirección")
    private String direccion;

    @NotNull(message = "Debe seleccionar una politica de cancelacion")
    private PoliticaCancelacion politicaCancelacion;

    @NotNull(message = "Debe seleccionar una politica de pagos")
    private PoliticaPagos politicaPagos;

    @Positive(message = "Los cupos totales deben ser mayores a 0")
    private int cuposTotales;

    public ProductoEntradaDto(){

    }

    public ProductoEntradaDto(String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, List<DiaSemana> diasDisponible, LocalDate fechaEvento, LocalDate fechaFinEvento, Set<Long> categoriasIds, Set<Long> caracteristicasIds, List<ProductoImagenEntradaDto> productoImagenes, String pais, String ciudad, String direccion, PoliticaCancelacion politicaCancelacion, PoliticaPagos politicaPagos, int cuposTotales) {
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
        this.fechaFinEvento = fechaFinEvento;
        this.categoriasIds = categoriasIds;
        this.caracteristicasIds = caracteristicasIds;
        this.productoImagenes = productoImagenes;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.politicaCancelacion = politicaCancelacion;
        this.politicaPagos = politicaPagos;
        this.cuposTotales = cuposTotales;
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

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public LocalDate getFechaFinEvento() {
        return fechaFinEvento;
    }

    public void setFechaFinEvento(LocalDate fechaFinEvento) {
        this.fechaFinEvento = fechaFinEvento;
    }

    @Positive(message = "Los cupos totales deben ser mayores a 0")
    public int getCuposTotales() {
        return cuposTotales;
    }

    public void setCuposTotales(int cuposTotales) {
        this.cuposTotales = cuposTotales;
    }
}
