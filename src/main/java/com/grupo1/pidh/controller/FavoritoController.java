package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.FavoritoEntradaDTO;
import com.grupo1.pidh.dto.salida.FavoritoSalidaDTO;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.service.impl.FavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("favoritos")
@Tag(name = "Favoritos", description = "Endpoints para gestionar los productos favoritos de usuarios")
public class FavoritoController {

    private final FavoritoService favoritoService;

    @Autowired
    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @Operation(summary = "Agregar producto a favoritos", description = "Agrega un producto a la lista de favoritos del usuario autenticado")
    @PostMapping("/agregar")
    public ResponseEntity<FavoritoSalidaDTO> agregarFavorito(@Valid @RequestBody FavoritoEntradaDTO favoritoEntradaDto)
            throws ResourceNotFoundException, ConflictException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        FavoritoSalidaDTO favoritoSalidaDto = favoritoService.agregarFavorito(userEmail, favoritoEntradaDto);
        return new ResponseEntity<>(favoritoSalidaDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar producto de favoritos", description = "Elimina un producto de la lista de favoritos del usuario autenticado")
    @DeleteMapping("/eliminar/{productoId}")
    public ResponseEntity<Map<String, String>> eliminarFavorito(@PathVariable Long productoId)
            throws ResourceNotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        favoritoService.eliminarFavorito(userEmail, productoId);
        return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado de favoritos correctamente"));
    }

    @Operation(summary = "Listar favoritos del usuario", description = "Muestra todos los productos favoritos del usuario autenticado")
    @GetMapping("/listar")
    public ResponseEntity<List<FavoritoSalidaDTO>> listarFavoritos() throws ResourceNotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<FavoritoSalidaDTO> favoritos = favoritoService.listarFavoritosPorUsuario(userEmail);
        return ResponseEntity.ok(favoritos);
    }

    @Operation(summary = "Verificar si un producto es favorito", description = "Verifica si un producto específico está en la lista de favoritos del usuario")
    @GetMapping("/verificar/{productoId}")
    public ResponseEntity<Map<String, Boolean>> verificarFavorito(@PathVariable Long productoId)
            throws ResourceNotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean esFavorito = favoritoService.verificarSiEsFavorito(userEmail, productoId);
        return ResponseEntity.ok(Map.of("esFavorito", esFavorito));
    }
}