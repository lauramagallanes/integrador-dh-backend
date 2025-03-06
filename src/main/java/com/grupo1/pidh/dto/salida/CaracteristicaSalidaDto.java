package com.grupo1.pidh.dto.salida;

public class CaracteristicaSalidaDto {

    private Long id;
    private String nombre;
    private String icono;

    public CaracteristicaSalidaDto(){}

    public CaracteristicaSalidaDto(Long id, String nombre, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
