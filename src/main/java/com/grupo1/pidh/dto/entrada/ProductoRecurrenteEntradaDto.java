package com.grupo1.pidh.dto.entrada;

import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoTarifa;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class ProductoRecurrenteEntradaDto extends ProductoEntradaDto {


    @NotEmpty(message = "Debe seleccionar al menos un dia disponible")
    private List<DiaSemana> diasDisponible;

    public ProductoRecurrenteEntradaDto(){}


    public ProductoRecurrenteEntradaDto(String nombre, String descripcion, Double valorTarifa, TipoTarifa tipoTarifa, String idioma, LocalTime horaInicio, LocalTime horaFin, String tipoEventoNombre, Set<String> categoriasNombres, List<Imagen> imagenes, List<DiaSemana> diasDisponible) {
        super(nombre, descripcion, valorTarifa, tipoTarifa, idioma, horaInicio, horaFin, tipoEventoNombre, categoriasNombres, imagenes);
        this.diasDisponible = diasDisponible;
    }

    public List<DiaSemana> getDiasDisponible() {
        return diasDisponible;
    }

    public void setDiasDisponible(List<DiaSemana> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }
}
