package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.service.impl.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("producto")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ProductoSalidaDto> crearProducto(@RequestBody ProductoEntradaDto dto) {
        ProductoSalidaDto productoSalidaDto = productoService.registrarProducto(dto);
        return new ResponseEntity<>(productoSalidaDto, HttpStatus.CREATED);

    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/listaAleatoria")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductosAleatorio() {
        return ResponseEntity.ok((productoService.listarProductosAleatorio()));
    }
}
