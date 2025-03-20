package com.grupo1.pidh.service;

import com.grupo1.pidh.dto.entrada.FavoritoEntradaDTO;
import com.grupo1.pidh.dto.salida.FavoritoSalidaDTO;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IFavoritoService {

    FavoritoSalidaDTO agregarFavorito(String userEmail, FavoritoEntradaDTO favoritoEntradaDto) throws ResourceNotFoundException, ConflictException;

    void eliminarFavorito(String userEmail, Long productoId) throws ResourceNotFoundException;

    List<FavoritoSalidaDTO> listarFavoritosPorUsuario(String userEmail) throws ResourceNotFoundException;

    boolean verificarSiEsFavorito(String userEmail, Long productoId) throws ResourceNotFoundException;
}