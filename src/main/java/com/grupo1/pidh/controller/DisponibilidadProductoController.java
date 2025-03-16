package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.salida.DisponibilidadProductoSalidaDto;
import com.grupo1.pidh.service.impl.DisponibilidadProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("disponibilidad")
@Tag(name = "Disponibilidad por producto", description = "Endpoint para consultar la disponibilidad de un producto")
public class DisponibilidadProductoController {
    private final DisponibilidadProductoService disponibilidadProductoService;

    @Autowired
    public DisponibilidadProductoController(DisponibilidadProductoService disponibilidadProductoService) {
        this.disponibilidadProductoService = disponibilidadProductoService;
    }

    @GetMapping("/{productoId}")
    @Operation(summary = "Consultar disponibilidad de un producto", description = "Devuelve la disponibilidad de un producto por su Id")
    public ResponseEntity<List<DisponibilidadProductoSalidaDto>> obtenerDisponibilidad(@PathVariable Long productoId){
        return ResponseEntity.ok(disponibilidadProductoService.obtenerDisponibilidadPorProducto(productoId));
    }

    @GetMapping("/listar")
    @Operation(summary = "Ver todas las disponibilidades", description = "Devuelve un listado con todas las disponibilidades para todos los productos")
    public ResponseEntity<List<DisponibilidadProductoSalidaDto>> listarDisponibilidades(){
        return ResponseEntity.ok(disponibilidadProductoService.listadoDisponibilidadTodosLosProductos());
    }
}
