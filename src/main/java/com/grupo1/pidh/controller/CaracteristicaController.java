package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.CaracteristicaEntradaDto;
import com.grupo1.pidh.dto.salida.CaracteristicaSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.service.impl.CaracteristicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("caracteristica")
@Tag(name = "Caracteristica", description = "Endpoint para CRUD de caracteristica")
public class CaracteristicaController {
    private final CaracteristicaService caracteristicaService;

    @Autowired
    public CaracteristicaController(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @Operation(summary = "Registrar nueva caracteristica", description = "Crea una nueva caracteristica en la base de datos")
    @PostMapping("/registrar")
    public ResponseEntity<CaracteristicaSalidaDto> crearCaracteristica(@Valid @RequestBody CaracteristicaEntradaDto dto) throws ConflictException {
        CaracteristicaSalidaDto caracteristicaSalidaDto = caracteristicaService.registrarCaracteristica(dto);
        return new ResponseEntity<>(caracteristicaSalidaDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar caracteristicas", description = "Lista todas las caracteristicas registradas en la BD")
    @GetMapping("/listar")
    public ResponseEntity<List<CaracteristicaSalidaDto>> listarCaracteristicas(){
        return ResponseEntity.ok(caracteristicaService.listarCaracteristicas());
    }

    @Operation(summary = "Buscar caracteristica por ID", description = "Devuelve una caracteristica segun su ID")
    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaSalidaDto> buscarCaracteristicaPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(caracteristicaService.buscarCaracteristicaPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar caracteristica", description = "Eliminar una caracteristica de la BD segun su ID")
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarCaracteristica(@RequestParam Long id) throws ResourceNotFoundException, ConflictException{
        caracteristicaService.eliminarCaracteristica(id);
        return new ResponseEntity<>("Caracteristica eliminada correctamente", HttpStatus.NO_CONTENT);
    }

}

