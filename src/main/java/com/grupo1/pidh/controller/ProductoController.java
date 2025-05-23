package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.FiltroProductoEntradaDto;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.dto.salida.ResenaProductoSalidaDto;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.service.impl.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.module.ResolutionException;
import java.util.Arrays;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("producto")
@Tag(name= "Producto", description = "Endpoints para CRUD de Productos")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Registrar un nuevo producto", description = "Crea un nuevo producto en la BD")
    @PostMapping("/registrar")
    public ResponseEntity<ProductoSalidaDto> crearProducto(
            @RequestPart("producto") @Valid ProductoEntradaDto dto,
            @RequestPart("imagenes") List<MultipartFile> imagenes) throws BadRequestException {
        ProductoSalidaDto productoSalidaDto = productoService.registrarProducto(dto, imagenes);
        return new ResponseEntity<>(productoSalidaDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar productos", description = "Lista todos los productos registrados en la BD")
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @Operation(summary = "Listado aleatorio de productos", description = "Lista todos los productos registrados en la BD en forma aleatoria")
    @GetMapping("/listaAleatoria")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductosAleatorio() {
        return ResponseEntity.ok((productoService.listarProductosAleatorio()));
    }

    @Operation(summary = "Buscar producto por ID", description = "Devuelve un producto registrado en la BD segun su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoSalidaDto> buscarProductoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.buscarProductoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto de la BD por su ID")
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarProducto(@RequestParam Long id) throws ResourceNotFoundException, ConflictException {
        productoService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Editar producto", description = "Edita o actualiza un producto ya existente en la BD")
    @PutMapping("/editar/{id}")
    public ResponseEntity<ProductoSalidaDto> editarProducto(@PathVariable Long id, @RequestPart("producto") @Valid ProductoEntradaDto dto, @RequestPart(value = "imagenes", required = false) List<MultipartFile> imagenes, HttpServletRequest request) throws ResourceNotFoundException, ConflictException, BadRequestException {
        ProductoSalidaDto productoSalidaDto = productoService.editarProducto(id, dto, imagenes);
        return new ResponseEntity<>(productoSalidaDto, HttpStatus.OK);
    }

    @Operation(summary = "Filtrar productos por nombre", description = "Filtra productos por nombre letra por letra")
    @GetMapping("/filtrar")
    public ResponseEntity<List<ProductoSalidaDto>> filtrarProductosPorNombre(@RequestParam String query){
        return ResponseEntity.ok(productoService.filtrarProductosPorNombre(query));
    }

    @Operation(summary = "Buscar productos con filtros", description = "Filtra productos por nombre, fecha de disponibilidad y categoria, siendo los filtros de busqueda opcionales")
    @PostMapping("/buscar")
    public ResponseEntity<List<ProductoSalidaDto>> buscarProductosPorFiltros(@RequestBody FiltroProductoEntradaDto dto){
        return ResponseEntity.ok(productoService.buscarProductosPorFiltros(dto));
    }

}
