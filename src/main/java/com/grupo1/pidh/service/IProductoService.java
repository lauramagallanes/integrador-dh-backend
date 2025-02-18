package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;

import java.lang.module.ResolutionException;
import java.util.List;

public interface IProductoService {
    ProductoSalidaDto registrarProducto(ProductoEntradaDto dto);
    List<ProductoSalidaDto> listarProductos();
    List<ProductoSalidaDto> listarProductosAleatorio();
    ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException;
    void eliminarProducto(Long id) throws ResourceNotFoundException;
}
