package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.ModificarUsuarioEntradaDto;
import com.grupo1.pidh.dto.entrada.ModificarUsuarioRoleEntradaDto;
import com.grupo1.pidh.dto.salida.UsuarioSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.service.impl.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Operation(summary = "Buscar usuario por mail", description = "brinda los datos detallados del usuario solicitado")
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioSalidaDto> buscarUsuarioPorMail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorMail(email));
    }

    @Operation(summary = "Modificar rol de otro usuario", description = "Modifica el rol de un usuario")
    @PutMapping("/modificarusuariorole")
    public ResponseEntity<UsuarioSalidaDto> modificarUsuarioRole(@Valid @RequestBody ModificarUsuarioRoleEntradaDto modificarUsuarioRoleEntradaDto) throws ConflictException {
        return ResponseEntity.ok(usuarioService.modificarUsuarioRole(modificarUsuarioRoleEntradaDto));
    }

    @Operation(summary = "Modificar usuario", description = "Modifica los datos de un usuario")
    @PutMapping("/modificarusuario")
    public ResponseEntity<UsuarioSalidaDto> modificarUsuario(@Valid @RequestBody ModificarUsuarioEntradaDto modificarUsuarioEntradaDto) throws ConflictException {
        return ResponseEntity.ok(usuarioService.modificarUsuario(modificarUsuarioEntradaDto));
    }
}
