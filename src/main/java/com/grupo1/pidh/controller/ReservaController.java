package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.AgregarResenaEntradaDto;
import com.grupo1.pidh.dto.entrada.ModificarReservasEntradaDTO;
import com.grupo1.pidh.dto.entrada.RegistrarReservasEntradaDTO;
import com.grupo1.pidh.dto.salida.ResenaDetalleSalidaDto;
import com.grupo1.pidh.dto.salida.ResenaProductoSalidaDto;
import com.grupo1.pidh.dto.salida.ReservaSalidaDTO;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.service.impl.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


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
    @Operation(summary = "Registrar nueva reserva", description = "Crea una reserva en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear reserva")
    })
    @PostMapping("/registrar")
    public ResponseEntity<ReservaSalidaDTO> crearReservas(
            @Valid @RequestBody RegistrarReservasEntradaDTO dto) throws BadRequestException, ConflictException {
        ReservaSalidaDTO reservaSalidaDTO = reservaService.registrarReserva(dto);
        return new ResponseEntity<>(reservaSalidaDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Agregar reseña y puntuación", description = "Permite agregar una reseña a la ultima reserva realizada por el usuario")
    @PostMapping("/agregar-resena")
    public ResponseEntity<ResenaDetalleSalidaDto> agregarResena(@Valid @RequestBody AgregarResenaEntradaDto dto, Authentication authentication) throws ConflictException, ResourceNotFoundException{

        String usuarioEmail = authentication.getName();
        ResenaDetalleSalidaDto resenaDetalleSalidaDto = reservaService.agregarResena(dto, usuarioEmail);
        return new ResponseEntity<>(resenaDetalleSalidaDto, HttpStatus.OK);

    }

    @Operation(summary = "Listar reservas por usuario", description = "Devuelve todas las reservas de un usuario específico.")
    @GetMapping("/resenas/{productoId}")
    public ResponseEntity<ResenaProductoSalidaDto> obtenerResenasPorProducto(@PathVariable Long productoId){
        ResenaProductoSalidaDto resenas = reservaService.obtenerResenasPorProducto(productoId);
        return ResponseEntity.ok(resenas);
    }
    @GetMapping("/usuario/{usuarioEmail}")
    public ResponseEntity<List<ReservaSalidaDTO>> listarPorUsuario(@PathVariable String usuarioEmail) {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(usuarioEmail));
    }
    @Operation(summary = "Listar reservas por producto", description = "Devuelve todas las reservas asociadas a un producto.")
    @GetMapping("/producto/{productoid}")
    public ResponseEntity<List<ReservaSalidaDTO>> listarPorProducto(@PathVariable Long productoid) {
        return ResponseEntity.ok(reservaService.listarReservasPorProducto(productoid));
    }
    @Operation(summary = "Listar reservas por disponibilidad de producto", description = "Devuelve reservas según la disponibilidad de un producto.")
    @GetMapping("/disponibilidadproducto/{disponibilidadproductoid}")
    public ResponseEntity<List<ReservaSalidaDTO>> listarPorDisponibilidadProducto(@PathVariable Long disponibilidadproductoid) {
        return ResponseEntity.ok(reservaService.listarReservasPorDisponibilidadProducto(disponibilidadproductoid));
    }
    @Operation(summary = "Listar todas las reservas", description = "Obtiene una lista de todas las reservas registradas.")
    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }
    @Operation(summary = "Buscar una reserva por ID", description = "Obtiene los detalles de una reserva específica.")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaSalidaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto al eliminar la reserva")
    })
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarReserva(@RequestParam Long id) throws ResourceNotFoundException, ConflictException {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Editar una reserva", description = "Modifica los datos de una reserva existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva editada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto al editar la reserva")
    })
    @PutMapping("/editar/{id}")
    public ResponseEntity<ReservaSalidaDTO> editarReserva(@PathVariable Long id, @Valid @RequestBody ModificarReservasEntradaDTO dto) throws ResourceNotFoundException, ConflictException {
        ReservaSalidaDTO reservaSalidaDTO = reservaService.editarReserva(id, dto);
        return new ResponseEntity<>(reservaSalidaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Listar reservas del usuario por producto", description = "Devuelve todas las reservas del usuario logueado para el producto especifico segun su Id.<br><br>" + "Ejemplo de uso:<br>" + "`GET /reserva/mis-reservas?productoId=5`")
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaSalidaDTO>> obtenerReservasDelUsuarioPorProducto(@RequestParam Long productoId, Authentication authentication) throws ResourceNotFoundException{
        String email = authentication.getName();
        List<ReservaSalidaDTO> reservasSalidaDto = reservaService.listarReservasPorUsuarioPorProducto(email, productoId);
        return ResponseEntity.ok(reservasSalidaDto);
    }
    @Operation(summary = "Cancelar una reserva", description = "Cancela una reserva existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva editada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto al editar la reserva")
    })
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<ReservaSalidaDTO> cancelar(@PathVariable Long id) throws ResourceNotFoundException, ConflictException {
        ReservaSalidaDTO reservaSalidaDTO = reservaService.cancelarReserva(id);
        return new ResponseEntity<>(reservaSalidaDTO, HttpStatus.OK);
    }
}
