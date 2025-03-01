package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.dto.salida.UsuarioSalidaDto;
import com.grupo1.pidh.service.impl.ProductoService;
import com.grupo1.pidh.service.impl.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@Tag(name= "Usuarios", description = "Endpoints de usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;
    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @Operation(summary = "Listar usuarios", description = "Lista todos los usuarios registrados en la BD")
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioSalidaDto>> listarProductos() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }
}
