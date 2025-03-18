package com.grupo1.pidh.controller;

import com.grupo1.pidh.dto.entrada.AgregarResenaEntradaDto;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.entrada.RegistrarReservasEntradaDTO;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
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
    public ResponseEntity<ReservaSalidaDTO> agregarResena(@Valid @RequestBody AgregarResenaEntradaDto dto, Authentication authentication) throws ConflictException, ResourceNotFoundException{
        System.out.println("Usuario autenticado" + authentication.getName());
        String usuarioEmail = authentication.getName();
        ReservaSalidaDTO reservaSalidaDTO = reservaService.agregarResena(dto, usuarioEmail);
        return new ResponseEntity<>(reservaSalidaDTO, HttpStatus.OK);
    }

}
