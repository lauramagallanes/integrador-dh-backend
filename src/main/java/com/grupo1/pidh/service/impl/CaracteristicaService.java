package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.dto.entrada.CaracteristicaEntradaDto;
import com.grupo1.pidh.dto.salida.CaracteristicaSalidaDto;
import com.grupo1.pidh.entity.Caracteristica;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.CaracteristicaRepository;
import com.grupo1.pidh.service.ICaracteristicaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaracteristicaService implements ICaracteristicaService {

    private final CaracteristicaRepository caracteristicaRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(CaracteristicaService.class);

    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto dto) throws ConflictException {
        Caracteristica caracteristica = new Caracteristica(null, dto.getNombre(), dto.getIcono());

        try {
            caracteristica = caracteristicaRepository.save(caracteristica);
            LOGGER.info("Característica registrada: {}", objectMapper.writeValueAsString(caracteristica));
        } catch (DataIntegrityViolationException exception) {
            LOGGER.error("Error al guardar la característica en la BD", exception);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una característica con este nombre o icono");
        } catch (Exception e) {
            LOGGER.error("Error al guardar la característica", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la característica");
        }

        return modelMapper.map(caracteristica, CaracteristicaSalidaDto.class);
    }

    @Override
    public List<CaracteristicaSalidaDto> listarCaracteristicas() {
        List<CaracteristicaSalidaDto> caracteristicasSalidaDto = caracteristicaRepository.findAll()
                .stream()
                .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaSalidaDto.class))
                .collect(Collectors.toList());

        try {
            LOGGER.info("Listado de características: {}", objectMapper.writeValueAsString(caracteristicasSalidaDto));
        } catch (Exception e) {
            LOGGER.error("Error serializando el listado de características", e);
        }

        return caracteristicasSalidaDto;
    }

    @Override
    public CaracteristicaSalidaDto buscarCaracteristicaPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Id de la característica a buscar: {}", id);

        CaracteristicaSalidaDto caracteristicaSalidaDto = caracteristicaRepository.findById(id)
                .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaSalidaDto.class))
                .orElse(null);

        if (caracteristicaSalidaDto == null) {
            LOGGER.error("Característica no encontrada");
            throw new ResourceNotFoundException("La característica solicitada no existe");
        }

        try {
            LOGGER.info("Característica encontrada: {}", objectMapper.writeValueAsString(caracteristicaSalidaDto));
        } catch (Exception e) {
            LOGGER.error("Error serializando la característica", e);
        }

        return caracteristicaSalidaDto;
    }

    @Override
    public void eliminarCaracteristica(Long id) throws ResourceNotFoundException, ConflictException {
        LOGGER.info("Id de la característica a eliminar: {}", id);

        if (buscarCaracteristicaPorId(id) != null) {
            try {
                caracteristicaRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                LOGGER.error("Característica con información relacionada");
                throw new ConflictException("La característica seleccionada no puede ser eliminada ya que está siendo usada en otras entidades");
            } catch (Exception e) {
                LOGGER.error("Error eliminando la característica", e);
                throw new RuntimeException("Error eliminando la característica");
            }

            LOGGER.warn("Se ha eliminado la característica con id {}", id);
        }
    }
}
