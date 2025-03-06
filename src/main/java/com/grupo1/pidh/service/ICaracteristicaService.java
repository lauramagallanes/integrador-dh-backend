package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.CaracteristicaEntradaDto;
import com.grupo1.pidh.dto.entrada.CategoriaEntradaDto;
import com.grupo1.pidh.dto.salida.CaracteristicaSalidaDto;
import com.grupo1.pidh.dto.salida.CategoriaSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICaracteristicaService {

    CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto dto) throws ConflictException;
    List<CaracteristicaSalidaDto> listarCaracteristicas();
    CaracteristicaSalidaDto buscarCaracteristicaPorId(Long id) throws ResourceNotFoundException;
    void eliminarCaracteristica(Long id) throws ResourceNotFoundException, ConflictException;

}
