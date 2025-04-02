package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.CategoriaEntradaDto;
import com.grupo1.pidh.dto.salida.CategoriaSalidaDto;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ICategoriaService {
    CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto dto, MultipartFile imagenCategoria) throws  ConflictException;
    List<CategoriaSalidaDto> listarCategorias();
    CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException;
    void eliminarCategoria(Long id) throws ResourceNotFoundException, ConflictException;
    CategoriaSalidaDto editarCategoria(Long id, CategoriaEntradaDto dto, MultipartFile imagenCategoria) throws ResourceNotFoundException, ConflictException;
    List<CategoriaSalidaDto> listarCategoriasDisponibles();
}
