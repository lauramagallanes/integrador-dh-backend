package com.grupo1.pidh.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoEvento;
import com.grupo1.pidh.utils.enums.TipoTarifa;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


public class ProductoSalidaDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double valorTarifa;
    private TipoTarifa tipoTarifa;
    private String idioma;

    @Schema(type = "string", example = "14:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime horaInicio;

    @Schema(type = "string", example = "14:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime horaFin;
    private TipoEvento tipoEvento;

    @Schema(type = "string", format = "date", example = "2025-02-25")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;
    private List<DiaSemana> diasDisponible;
    private Set<CategoriaSalidaDto> categorias;
    private Set<CaracteristicaSalidaDto> caracteristicas;
    private List<ProductoImagenSalidaDto> productoImagenesSalidaDto;

    public ProductoSalidaDto(){

    }

    public ProductoSalidaDto(Long id, String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, TipoEvento tipoEvento, LocalDate fechaEvento, List<DiaSemana> diasDisponible, Set<CategoriaSalidaDto> categorias, Set<CaracteristicaSalidaDto> caracteristicas, List<ProductoImagenSalidaDto> productoImagenesSalidaDto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorTarifa = valorTarifa;
        this.tipoTarifa = tipoTarifa;
        this.idioma = idioma;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoEvento = tipoEvento;
        this.fechaEvento = fechaEvento;
        this.diasDisponible = diasDisponible;
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
        this.productoImagenesSalidaDto = productoImagenesSalidaDto;
    }

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

    public List<ProductoImagenSalidaDto> getProductoImagenesSalidaDto() {
        return productoImagenesSalidaDto;
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

    public Set<CategoriaSalidaDto> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaSalidaDto> categorias) {
        this.categorias = categorias;
    }

    public void setProductoImagenesSalidaDto(List<ProductoImagenSalidaDto> productoImagenesSalidaDto) {
        this.productoImagenesSalidaDto = productoImagenesSalidaDto;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public List<DiaSemana> getDiasDisponible() {
        return diasDisponible;
    }

    public void setDiasDisponible(List<DiaSemana> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }

    public Set<CaracteristicaSalidaDto> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<CaracteristicaSalidaDto> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
