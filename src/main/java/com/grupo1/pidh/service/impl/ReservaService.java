package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.dto.entrada.AgregarResenaEntradaDto;
import com.grupo1.pidh.dto.entrada.ModificarReservasEntradaDTO;
import com.grupo1.pidh.dto.entrada.RegistrarReservasEntradaDTO;
import com.grupo1.pidh.dto.salida.ResenaDetalleSalidaDto;
import com.grupo1.pidh.dto.salida.ResenaProductoSalidaDto;
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
import com.grupo1.pidh.utils.enums.EstadoReserva;
import com.grupo1.pidh.utils.enums.TipoTarifa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {

    private final ReservaRepository reservaRepository;
    private final DisponibilidadProductoRepository disponibilidadProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    public ReservaService(ReservaRepository reservaRepository, DisponibilidadProductoRepository disponibilidadProductoRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.disponibilidadProductoRepository = disponibilidadProductoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
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
    public ReservaSalidaDTO registrarReserva(RegistrarReservasEntradaDTO dto) throws BadRequestException, ResourceNotFoundException, ConflictException {

        if (dto.getDisponibilidadProductoId() == null){
            throw new BadRequestException("Debe ingresar una fecha disponible del producto");
        }else if (dto.getUsuarioEmail() == null){
            throw new BadRequestException("Debe ingresar un usuario");
        }else if (dto.getCantidadPersonas() == 0){
            throw new BadRequestException("Debe indicar cuantas personas incluye la reserva");
        }

        DisponibilidadProducto disponibilidadProducto = disponibilidadProductoRepository.findById(dto.getDisponibilidadProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Fecha disponible del producto no encontrada"));

        Usuario usuario = usuarioRepository.findByEmail(dto.getUsuarioEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        LocalDateTime fechaHoraInicioEvento = disponibilidadProducto.getFechaEvento().atTime(disponibilidadProducto.getProducto().getHoraInicio());
        LocalDateTime fechaHoraFinEvento = disponibilidadProducto.getFechaEvento().atTime(disponibilidadProducto.getProducto().getHoraFin());
        LocalDateTime ahora = LocalDateTime.now();

        if (fechaHoraFinEvento.isBefore(ahora)) {
            throw new ConflictException("La actividad ya finalizó");
        }else if (fechaHoraInicioEvento.isBefore(ahora)){
            throw new ConflictException("La actividad ya empezó");
        }

        if ((disponibilidadProducto.getCuposReservados() + dto.getCantidadPersonas()) > disponibilidadProducto.getCuposTotales()){
            throw new ConflictException("No hay cupos suficientes para realizar la reserva.");
        }

        disponibilidadProducto.setCuposReservados(disponibilidadProducto.getCuposReservados() + dto.getCantidadPersonas());
        Reserva reserva = new Reserva(null, disponibilidadProducto, usuario, dto.getCantidadPersonas(), UUID.randomUUID().toString(), false);
        ReservaSalidaDTO reservaSalidaDTO = modelMapper.map(reservaRepository.save(reserva), ReservaSalidaDTO.class);

        disponibilidadProductoRepository.save(disponibilidadProducto);


        return reservaSalidaDTO;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservas() {
        List<ReservaSalidaDTO> reservaSalidaDTOList = reservaRepository.findAll()
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .toList();
        return reservaSalidaDTOList;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservasPorUsuario(String usuarioEmail) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<ReservaSalidaDTO> reservaSalidaDTOList = reservaRepository.findByUsuario(usuario)
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .toList();
        return reservaSalidaDTOList;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservasPorProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        List<ReservaSalidaDTO> reservaSalidaDTOList = reservaRepository.findByDisponibilidadProducto_Producto(producto)
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .toList();
        return reservaSalidaDTOList;
    }

    @Override
    public List<ReservaSalidaDTO> listarReservasPorDisponibilidadProducto(Long disponibilidadProductoId) {
        DisponibilidadProducto disponibilidadProducto = disponibilidadProductoRepository.findById(disponibilidadProductoId)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad no encontrada"));;
        List<ReservaSalidaDTO> reservaSalidaDTOList = reservaRepository.findByDisponibilidadProducto(disponibilidadProducto)
                .stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .toList();
        return reservaSalidaDTOList;
    }

    @Override
    public ReservaSalidaDTO buscarReservaPorId(Long id) throws ResourceNotFoundException {
        return  reservaRepository.findById(id)
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrado"));
    }

    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException, ConflictException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrado"));
        LocalDateTime fechaHoraInicioEvento = reserva.getDisponibilidadProducto().getFechaEvento().atTime(reserva.getDisponibilidadProducto().getProducto().getHoraInicio());
        LocalDateTime fechaHoraFinEvento = reserva.getDisponibilidadProducto().getFechaEvento().atTime(reserva.getDisponibilidadProducto().getProducto().getHoraFin());
        LocalDateTime ahora = LocalDateTime.now();

        if (fechaHoraFinEvento.isBefore(ahora)) {
            throw new ConflictException("La reserva que quiere eliminar ya finalizó");
        }else if (fechaHoraInicioEvento.isBefore(ahora)){
            throw new ConflictException("La reserva que quiere eliminar ya empezó");
        }
        DisponibilidadProducto actualDisponibilidadProducto = reserva.getDisponibilidadProducto();

        actualDisponibilidadProducto.setCuposReservados(actualDisponibilidadProducto.getCuposReservados() - reserva.getCantidadPersonas());
        actualDisponibilidadProducto = disponibilidadProductoRepository.save(actualDisponibilidadProducto);
        reservaRepository.delete(reserva);
    }

    @Override
    public ReservaSalidaDTO editarReserva(Long id, ModificarReservasEntradaDTO dto) throws ResourceNotFoundException, ConflictException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        switch(reserva.getEstado()){
            case CANCELADA:
                throw new ConflictException("No se puede modificar una reserva cancelada");
            case FINALIZADA:
                throw new ConflictException("No se puede modificar una reserva finalizada");
        }
        LocalDateTime fechaHoraInicioEvento = reserva.getDisponibilidadProducto().getFechaEvento().atTime(reserva.getDisponibilidadProducto().getProducto().getHoraInicio());
        LocalDateTime fechaHoraFinEvento = reserva.getDisponibilidadProducto().getFechaEvento().atTime(reserva.getDisponibilidadProducto().getProducto().getHoraFin());
        LocalDateTime ahora = LocalDateTime.now();

        if (fechaHoraFinEvento.isBefore(ahora)) {
            throw new ConflictException("La reserva que quiere modificar ya finalizó");
        }else if (fechaHoraInicioEvento.isBefore(ahora)){
            throw new ConflictException("La reserva que quiere modificar ya empezó");
        }
        DisponibilidadProducto actualDisponibilidadProducto = reserva.getDisponibilidadProducto();

        if (!Objects.equals(reserva.getUsuario().getEmail(), dto.getUsuarioEmail())){
            Usuario usuario = usuarioRepository.findByEmail(dto.getUsuarioEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            reserva.setUsuario(usuario);
        }

        if (!Objects.equals(reserva.getDisponibilidadProducto().getId(), dto.getDisponibilidadProductoId())){
            actualDisponibilidadProducto.setCuposReservados(actualDisponibilidadProducto.getCuposReservados() - reserva.getCantidadPersonas());
            DisponibilidadProducto nuevaDisponibilidadProducto = disponibilidadProductoRepository.findById(dto.getDisponibilidadProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad no encontrada"));

            if ((nuevaDisponibilidadProducto.getCuposReservados() + dto.getCantidadPersonas()) > nuevaDisponibilidadProducto.getCuposTotales()){
                throw new ConflictException("No hay cupos suficientes para realizar la reserva.");
            }

            nuevaDisponibilidadProducto.setCuposReservados(nuevaDisponibilidadProducto.getCuposReservados() + dto.getCantidadPersonas());
            reserva.setDisponibilidadProducto(nuevaDisponibilidadProducto);
            reserva.setCantidadPersonas(dto.getCantidadPersonas());
            actualDisponibilidadProducto = disponibilidadProductoRepository.save(actualDisponibilidadProducto);
            nuevaDisponibilidadProducto = disponibilidadProductoRepository.save(nuevaDisponibilidadProducto);

        } else if (reserva.getCantidadPersonas() != dto.getCantidadPersonas()){
            int cantidadPersonasDiferencia = dto.getCantidadPersonas() - reserva.getCantidadPersonas();

            if ((actualDisponibilidadProducto.getCuposReservados() + cantidadPersonasDiferencia) > actualDisponibilidadProducto.getCuposTotales()){
                throw new ConflictException("No hay cupos suficientes para realizar la reserva.");
            }
            actualDisponibilidadProducto.setCuposReservados(actualDisponibilidadProducto.getCuposReservados() + cantidadPersonasDiferencia);
            reserva.setCantidadPersonas(reserva.getCantidadPersonas() + cantidadPersonasDiferencia);
            reserva.setDisponibilidadProducto(actualDisponibilidadProducto);

            actualDisponibilidadProducto = disponibilidadProductoRepository.save(actualDisponibilidadProducto);
        }

        return modelMapper.map(reservaRepository.save(reserva), ReservaSalidaDTO.class);
    }

    @Override
    public ResenaDetalleSalidaDto agregarResena(AgregarResenaEntradaDto dto, String usuarioEmail) throws ResourceNotFoundException, ConflictException {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        Reserva reserva = reservaRepository.findById(dto.getReservaId())
                .orElseThrow(()->new ResourceNotFoundException("Reserva no encontrada"));

        if (!reserva.getUsuario().getEmail().equals(usuarioEmail)){
            throw new ConflictException("Esta reserva no pertenece al usuario autenticado");
        }

        if (reserva.getResena() != null){
            throw new ConflictException("Ya existe una reseña para esta reserva");
        }
        reserva.setResena(dto.getResena());
        reserva.setPuntuacion(dto.getPuntuacion());
        reserva.setFechaResena(LocalDate.now());

        Reserva reservaGuardada = reservaRepository.save(reserva);



        return new ResenaDetalleSalidaDto(
                reservaGuardada.getUsuario().getNombre(),
                reservaGuardada.getPuntuacion(),
                reservaGuardada.getResena(),
                reservaGuardada.getFechaResena()
        );
    }

    @Override
    public ResenaProductoSalidaDto obtenerResenasPorProducto(Long productoId) {
        List<Reserva> reservas = reservaRepository.findResenasByProductoId(productoId);
        if (reservas.isEmpty()){
            return new ResenaProductoSalidaDto(productoId, 0, 0, new ArrayList<>());
        }
        List<ReservaSalidaDTO> reservasDto = reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .collect(Collectors.toList());

        List<ResenaDetalleSalidaDto> listaResenas = reservasDto.stream()
                .map(reservaDto -> new ResenaDetalleSalidaDto(
                        reservaDto.getUsuarioSalidaDTO().getNombre(),
                        reservaDto.getPuntuacion(),
                        reservaDto.getResena(),
                        reservaDto.getFechaResena()
                )).collect(Collectors.toList());

        double promedioPuntuacion = reservasDto.stream()
                .mapToInt(ReservaSalidaDTO::getPuntuacion)
                .average()
                .orElse(0.0);
        return new ResenaProductoSalidaDto(productoId, promedioPuntuacion, reservasDto.size(), listaResenas);
    }

    @Override
    public List<ReservaSalidaDTO> listarReservasPorUsuarioPorProducto(String email, Long productoId) throws ResourceNotFoundException {

        if (productoId == null){
            throw new ResourceNotFoundException("Debe proporcionar el ID del producto para poder realizar la consulta");
        }

        List<Reserva> reservas = reservaRepository.findByUsuarioEmailAndProductoId(email, productoId);

        if (reservas.isEmpty()){
            throw new ResourceNotFoundException("El usuario no tiene reservas para el producto con id " + productoId);
        }
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaSalidaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReservaSalidaDTO cancelarReserva(Long id) throws ResourceNotFoundException, ConflictException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        switch(reserva.getEstado()){
            case CANCELADA:
                throw new ConflictException("No se puede cancelar una reserva cancelada");
            case FINALIZADA:
                throw new ConflictException("No se puede cancelar una reserva finalizada");
        }
        reserva.setEstaCancelada(true);
        DisponibilidadProducto disponibilidadProducto = reserva.getDisponibilidadProducto();
        disponibilidadProducto.setCuposReservados(disponibilidadProducto.getCuposReservados() - reserva.getCantidadPersonas());
       return modelMapper.map(reservaRepository.save(reserva), ReservaSalidaDTO.class);
    }
}
