package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.AgregarResenaEntradaDto;
import com.grupo1.pidh.dto.entrada.ModificarReservasEntradaDTO;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.entrada.RegistrarReservasEntradaDTO;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.dto.salida.ResenaDetalleSalidaDto;
import com.grupo1.pidh.dto.salida.ResenaProductoSalidaDto;
import com.grupo1.pidh.dto.salida.ReservaSalidaDTO;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.service.impl.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("reserva")
@Tag(name= "Reseva", description = "Endpoints para CRUD de Reserva")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @Operation(summary = "Registrar una o varias nuevas reservas", description = "Crea una o varias nuevas reservas en la BD")
    @PostMapping("/registrar")
    public ResponseEntity<List<ReservaSalidaDTO>> crearReservas(
            @Valid @RequestBody RegistrarReservasEntradaDTO dto) throws BadRequestException, ConflictException {
        List<ReservaSalidaDTO> reservaSalidaDTOList = reservaService.registrarReservas(dto);
        return new ResponseEntity<>(reservaSalidaDTOList, HttpStatus.CREATED);
    }

    @Operation(summary = "Agregar reseña y puntuación", description = "Permite agregar una reseña a la ultima reserva realizada por el usuario")
    @PostMapping("/agregar-resena")
    public ResponseEntity<ResenaDetalleSalidaDto> agregarResena(@Valid @RequestBody AgregarResenaEntradaDto dto, Authentication authentication) throws ConflictException, ResourceNotFoundException{

        String usuarioEmail = authentication.getName();
        ResenaDetalleSalidaDto resenaDetalleSalidaDto = reservaService.agregarResena(dto, usuarioEmail);
        return new ResponseEntity<>(resenaDetalleSalidaDto, HttpStatus.OK);

    }

    @Operation(summary = "Obtener reseñas de un producto", description = "Devuelve todas las reseñas de un producto, con el promedio de puntuaciones y el total de reseñas existentes")
    @GetMapping("/resenas/{productoId}")
    public ResponseEntity<ResenaProductoSalidaDto> obtenerResenasPorProducto(@PathVariable Long productoId){
        ResenaProductoSalidaDto resenas = reservaService.obtenerResenasPorProducto(productoId);
        return ResponseEntity.ok(resenas);
    }
    @GetMapping("/usuario/{usuarioEmail}")
    public ResponseEntity<List<ReservaSalidaDTO>> listarPorUsuario(@PathVariable String usuarioEmail) {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(usuarioEmail));
    }
    @GetMapping("/producto/{productoid}")
    public ResponseEntity<List<ReservaSalidaDTO>> listarPorProducto(@PathVariable Long productoid) {
        return ResponseEntity.ok(reservaService.listarReservasPorProducto(productoid));
    }
    @GetMapping("/disponibilidadproducto/{disponibilidadproductoid}")
    public ResponseEntity<List<ReservaSalidaDTO>> listarPorDisponibilidadProducto(@PathVariable Long disponibilidadproductoid) {
        return ResponseEntity.ok(reservaService.listarReservasPorDisponibilidadProducto(disponibilidadproductoid));
    }
    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReservaSalidaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarReserva(@RequestParam Long id) throws ResourceNotFoundException, ConflictException {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.NO_CONTENT);
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<ReservaSalidaDTO> editarReserva(@PathVariable Long id, @Valid @RequestBody ModificarReservasEntradaDTO dto) throws ResourceNotFoundException, ConflictException {
        ReservaSalidaDTO reservaSalidaDTO = reservaService.editarReserva(id, dto);
        return new ResponseEntity<>(reservaSalidaDTO, HttpStatus.OK);
    }

}
