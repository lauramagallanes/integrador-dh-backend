package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.dto.entrada.AgregarResenaEntradaDto;
import com.grupo1.pidh.dto.entrada.RegistrarReservasEntradaDTO;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.dto.salida.ReservaSalidaDTO;
import com.grupo1.pidh.entity.DisponibilidadProducto;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.entity.Reserva;
import com.grupo1.pidh.entity.Usuario;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.DisponibilidadProductoRepository;
import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.repository.ReservaRepository;
import com.grupo1.pidh.repository.UsuarioRepository;
import com.grupo1.pidh.service.IReservaService;
import com.grupo1.pidh.utils.enums.TipoTarifa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    private final ReservaRepository reservaRepository;
    private final DisponibilidadProductoRepository disponibilidadProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public ReservaService(ReservaRepository reservaRepository, DisponibilidadProductoRepository disponibilidadProductoRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.disponibilidadProductoRepository = disponibilidadProductoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    private void configureMapping() {
        modelMapper.typeMap(RegistrarReservasEntradaDTO.class, Reserva.class);
        modelMapper.typeMap(Reserva.class, ReservaSalidaDTO.class)
                .addMappings(mapper -> mapper.map(Reserva::getDisponibilidadProducto, ReservaSalidaDTO::setDisponibilidadProductoSalidaDto))
                .addMappings(mapper -> mapper.map(Reserva::getUsuario, ReservaSalidaDTO::setUsuarioSalidaDto));
    }

    @Override
    public List<ReservaSalidaDTO> registrarReservas(RegistrarReservasEntradaDTO dto) throws BadRequestException, ResourceNotFoundException, ConflictException {
        List<ReservaSalidaDTO> reservaSalidaDTOList = new ArrayList<>();
        if (dto.getDisponibilidadProductoId() == null){
            throw new BadRequestException("Debe ingresar una fecha disponible del producto");
        }else if (dto.getUsuarioEmail() == null){
            throw new BadRequestException("Debe ingresar un usuario");
        }else if (dto.getTipoTarifa() == null){
            throw new BadRequestException("Debe ingresar una tarifa");
        }else if (dto.getCantidadReservas() == 0){
            throw new BadRequestException("Debe indicar cuantas reservas quiere reservar");
        }

        DisponibilidadProducto disponibilidadProducto = disponibilidadProductoRepository.findById(dto.getDisponibilidadProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Fecha disponible del producto no encontrada"));

        Usuario usuario = usuarioRepository.findByEmail(dto.getUsuarioEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));


        int cantidadPersonas = obtenerCantidadPersonas(dto.getTipoTarifa());
        int cuposAReservar = cantidadPersonas * dto.getCantidadReservas();
        if ((disponibilidadProducto.getCuposReservados() + cuposAReservar) > disponibilidadProducto.getCuposTotales()){
            throw new ConflictException("No hay cupos suficientes para realizar la reserva.");
        }



        for (int i = 1; i <= dto.getCantidadReservas(); i++) {
            disponibilidadProducto.setCuposReservados(disponibilidadProducto.getCuposReservados() + cantidadPersonas);
            Reserva reserva = new Reserva(null, disponibilidadProducto, usuario, cantidadPersonas);
            reservaSalidaDTOList.add(modelMapper.map(reservaRepository.save(reserva), ReservaSalidaDTO.class));
            
        }
        disponibilidadProductoRepository.save(disponibilidadProducto);


        return reservaSalidaDTOList;
    }

    private int obtenerCantidadPersonas(TipoTarifa tipoTarifa) {
        int cantidadPersonas = 0;
        switch (tipoTarifa){
            case POR_PERSONA:
                cantidadPersonas = 1;
                break;
            case POR_PAREJA:
                cantidadPersonas = 2;
                break;
            case POR_GRUPO_6:
                cantidadPersonas = 6;
                break;
            case POR_GRUPO_10:
                cantidadPersonas = 10;
                break;

        }
        return cantidadPersonas;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservas() {
        return null;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservasPorUsuario() {
        return null;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservasPorProducto() {
        return null;
    }

    @Override
    public ReservaSalidaDTO buscarReservaPorId(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException, ConflictException {

    }

    @Override
    public ReservaSalidaDTO editarProducto(Long id, RegistrarReservasEntradaDTO dto) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public ReservaSalidaDTO agregarResena(AgregarResenaEntradaDto dto, String usuarioEmail) throws ResourceNotFoundException, ConflictException {
        Reserva reserva = reservaRepository.findFirstByUsuarioEmailOrderByIdDesc(usuarioEmail).orElseThrow(()->new ResourceNotFoundException("No se encontró ninguna reserva activa para el usuario"));

        if (reserva.getPuntuacion() != null){
            throw new ConflictException("Esta reserva ya ha sido calificada");
        }

        reserva.setPuntuacion(dto.getPuntuacion());
        reserva.setResena(dto.getResena());
        reserva.setFechaResena(LocalDate.now());

        reservaRepository.save(reserva);

        return modelMapper.map(reserva, ReservaSalidaDTO.class);
    }
}
