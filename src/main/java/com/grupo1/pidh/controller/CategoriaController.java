package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.CategoriaEntradaDto;
import com.grupo1.pidh.dto.salida.CategoriaSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.service.impl.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("categoria")
@Tag(name= "Categorias", description = "Endpoint para CRUD de categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Registrar nueva categoria", description = "Crea una nueva categoria dentro de la base de datos")
    @PostMapping("/registrar")
    public ResponseEntity<CategoriaSalidaDto> crearCategoria(@RequestPart("categoria") @Valid CategoriaEntradaDto dto, @RequestPart(value = "imagenCategoria", required = false)MultipartFile imagenCategoria) throws ConflictException {
        CategoriaSalidaDto categoriaSalidaDto = categoriaService.registrarCategoria(dto, imagenCategoria);
        return new ResponseEntity<>(categoriaSalidaDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar categorias", description = "Lista todas las categorias registradas en la DB")
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaSalidaDto>> listarCategorias(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @Operation(summary = "Buscar categoria por ID", description = "Devuelve una categoría segun su ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaSalidaDto> buscarCategoriaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoriaService.buscarCategoriaPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria de la DB por su ID")
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarCategoria(@RequestParam Long id) throws ResourceNotFoundException, ConflictException{
        categoriaService.eliminarCategoria(id);
        return new ResponseEntity<>("Categoria eliminada correctamente", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Editar categoria", description = """
    Edita una categoría existente por su ID. 
    \n\n**Parámetros esperados**:
    - `categoria`: objeto JSON con nombre y descripción (obligatorio).
    - `imagenCategoria`: archivo de imagen (opcional).
    
    \n\n**Comportamiento**:
    - Si se envía una nueva imagen, la imagen anterior será eliminada del servidor (S3) y reemplazada por la nueva.
    - Si no se envía una imagen, se conserva la imagen actual asociada a la categoría.
    
    \n\n**Restricciones**:
    - El nombre debe ser único.
    - La imagen debe ser válida y cumplir con los requisitos definidos (tipo y tamaño).
    """)
    @PutMapping("/editar/{id}")
    public ResponseEntity<CategoriaSalidaDto> editarCategoria(@PathVariable Long id, @RequestPart("categoria") @Valid CategoriaEntradaDto dto, @RequestPart(value = "imagenCategoria", required = false) MultipartFile imagenCategoria) throws ResourceNotFoundException, ConflictException{
        CategoriaSalidaDto categoriaSalidaDto =categoriaService.editarCategoria(id, dto, imagenCategoria);
        return new ResponseEntity<>(categoriaSalidaDto, HttpStatus.OK);
    }

    @Operation(summary = "Listar categorias reservables", description = "Lista todas las categorias reservables en la DB")
    @GetMapping("/listarreservables")
    public ResponseEntity<List<CategoriaSalidaDto>> listarReservables(){
        return ResponseEntity.ok(categoriaService.listarCategoriasDisponibles());
    }
}
