package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.dto.salida.DisponibilidadProductoSalidaDto;
import com.grupo1.pidh.entity.DisponibilidadProducto;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.DisponibilidadProductoRepository;
import com.grupo1.pidh.service.IDisponibilidadProductoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibilidadProductoService implements IDisponibilidadProductoService {
    private final DisponibilidadProductoRepository disponibilidadProductoRepository;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);


    public DisponibilidadProductoService(DisponibilidadProductoRepository disponibilidadProductoRepository, ModelMapper modelMapper) {
        this.disponibilidadProductoRepository = disponibilidadProductoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DisponibilidadProductoSalidaDto> obtenerDisponibilidadPorProducto(Long productoId) {
        List<DisponibilidadProducto> disponibilidades = disponibilidadProductoRepository.findByProductoId(productoId);

        if (disponibilidades.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron disponibilidades para este producto");
        }

        return disponibilidades.stream()
                .map(disponibilidad -> modelMapper.map(disponibilidad, DisponibilidadProductoSalidaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DisponibilidadProductoSalidaDto> listadoDisponibilidadTodosLosProductos() {
        List<DisponibilidadProductoSalidaDto> listadoDisponibilidades = disponibilidadProductoRepository.findAll().stream()
                .map(disponibilidad -> modelMapper.map(disponibilidad, DisponibilidadProductoSalidaDto.class))
                .toList();
        try {
            LOGGER.info("Mostrando el listado de disponibilidades para todos los productos");
        } catch (Exception e) {
            LOGGER.error("Error serializando el listado de disponibilidades para los productos", e);
        }
        return listadoDisponibilidades;
    }

}
