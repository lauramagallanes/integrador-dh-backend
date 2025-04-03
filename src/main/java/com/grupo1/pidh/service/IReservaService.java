package com.grupo1.pidh.service;

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

import java.util.List;

public interface IReservaService {
    ReservaSalidaDTO registrarReserva(RegistrarReservasEntradaDTO dto) throws BadRequestException, ConflictException;
    List<ReservaSalidaDTO> listarReservas();
    List<ReservaSalidaDTO> listarReservasPorUsuario(String usuarioEmail);
    List<ReservaSalidaDTO> listarReservasPorProducto(Long id);
    List<ReservaSalidaDTO> listarReservasPorDisponibilidadProducto(Long id);

    ReservaSalidaDTO buscarReservaPorId(Long id) throws ResourceNotFoundException;
    void eliminarReserva(Long id) throws ResourceNotFoundException, ConflictException;
    ReservaSalidaDTO editarReserva(Long id, ModificarReservasEntradaDTO dto) throws ResourceNotFoundException, ConflictException;
    ResenaDetalleSalidaDto agregarResena(AgregarResenaEntradaDto dto, String usuarioEmail) throws ResourceNotFoundException, ConflictException;
    ResenaProductoSalidaDto obtenerResenasPorProducto(Long productoId);
    List<ReservaSalidaDTO> listarReservasPorUsuarioPorProducto(String email, Long productoId) throws ResourceNotFoundException;

    ReservaSalidaDTO cancelarReserva(Long id) throws ResourceNotFoundException, ConflictException;

}
