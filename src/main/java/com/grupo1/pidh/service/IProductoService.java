package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;

import java.util.List;

public interface IProductoService {
    ProductoSalidaDto registrarProducto(ProductoEntradaDto dto);
    List<ProductoSalidaDto> listarProductos();
    List<ProductoSalidaDto> listarProductosAleatorio();
//    ProductoSalidaDto buscarProductoPorId(Long id);
}
