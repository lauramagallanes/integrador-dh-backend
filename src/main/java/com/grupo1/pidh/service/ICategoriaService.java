package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.CategoriaEntradaDto;
import com.grupo1.pidh.dto.salida.CategoriaSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;

import java.util.List;


public interface ICategoriaService {
    CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto dto) throws  ConflictException;
    List<CategoriaSalidaDto> listarCategorias();
    CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException;
    void eliminarCategoria(Long id) throws ResourceNotFoundException, ConflictException;

}
