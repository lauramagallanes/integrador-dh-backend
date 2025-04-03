package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.salida.DisponibilidadProductoSalidaDto;

import java.util.List;

public interface IDisponibilidadProductoService {
    List<DisponibilidadProductoSalidaDto> obtenerDisponibilidadPorProducto(Long productoId);
    List <DisponibilidadProductoSalidaDto> listadoDisponibilidadTodosLosProductos();
}
