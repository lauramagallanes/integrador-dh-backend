package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.lang.module.ResolutionException;
import java.util.List;

public interface IProductoService {
    ProductoSalidaDto registrarProducto(ProductoEntradaDto dto, List<MultipartFile> imagenes) throws BadRequestException;
    List<ProductoSalidaDto> listarProductos();
    List<ProductoSalidaDto> listarProductosAleatorio();
    ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException;
    void eliminarProducto(Long id) throws ResourceNotFoundException, ConflictException;
    ProductoSalidaDto editarProducto(Long id, ProductoEntradaDto dto, List<MultipartFile> imagenes) throws ResourceNotFoundException;
}