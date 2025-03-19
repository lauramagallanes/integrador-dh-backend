package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.AgregarResenaEntradaDto;
import com.grupo1.pidh.dto.entrada.RegistrarReservasEntradaDTO;
import com.grupo1.pidh.dto.salida.ResenaDetalleSalidaDto;
import com.grupo1.pidh.dto.salida.ResenaProductoSalidaDto;
import com.grupo1.pidh.dto.salida.ReservaSalidaDTO;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IReservaService {
    List<ReservaSalidaDTO> registrarReservas(RegistrarReservasEntradaDTO dto) throws BadRequestException, ConflictException;
    List<ReservaSalidaDTO> listarReservas();
    List<ReservaSalidaDTO> listarReservasPorUsuario();
    List<ReservaSalidaDTO> listarReservasPorProducto();
    ReservaSalidaDTO buscarReservaPorId(Long id) throws ResourceNotFoundException;
    void eliminarReserva(Long id) throws ResourceNotFoundException, ConflictException;
    ReservaSalidaDTO editarProducto(Long id, RegistrarReservasEntradaDTO dto) throws ResourceNotFoundException;
    ResenaDetalleSalidaDto agregarResena(AgregarResenaEntradaDto dto, String usuarioEmail) throws ResourceNotFoundException, ConflictException;
    ResenaProductoSalidaDto obtenerResenasPorProducto(Long productoId);

}
