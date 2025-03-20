package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.dto.entrada.FavoritoEntradaDTO;
import com.grupo1.pidh.dto.salida.FavoritoSalidaDTO;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.entity.Favorito;
import com.grupo1.pidh.entity.Usuario;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.FavoritoRepository;
import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.repository.UsuarioRepository;
import com.grupo1.pidh.service.IFavoritoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritoService implements IFavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(FavoritoService.class);

    public FavoritoService(FavoritoRepository favoritoRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public FavoritoSalidaDTO agregarFavorito(String userEmail, FavoritoEntradaDTO favoritoEntradaDto) throws ResourceNotFoundException, ConflictException {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(favoritoEntradaDto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Verificar si ya existe el favorito
        Optional<Favorito> existente = favoritoRepository.findByUsuarioIdAndProductoId(
                usuario.getId(), producto.getId());

        if (existente.isPresent()) {
            throw new ConflictException("El producto ya está en favoritos");
        }

        Favorito favorito = new Favorito(usuario, producto);
        favorito = favoritoRepository.save(favorito);

        LOGGER.info("Favorito agregado: Usuario ID {} - Producto ID {}", usuario.getId(), producto.getId());

        FavoritoSalidaDTO favoritoSalidaDTO = new FavoritoSalidaDTO();
        favoritoSalidaDTO.setId(favorito.getId());
        favoritoSalidaDTO.setProducto(modelMapper.map(producto, com.grupo1.pidh.dto.salida.ProductoSalidaDto.class));

        return favoritoSalidaDTO;
    }

    @Override
    @Transactional
    public void eliminarFavorito(String userEmail, Long productoId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Optional<Favorito> favorito = favoritoRepository.findByUsuarioIdAndProductoId(
                usuario.getId(), productoId);

        if (!favorito.isPresent()) {
            throw new ResourceNotFoundException("Favorito no encontrado");
        }

        favoritoRepository.delete(favorito.get());
        LOGGER.info("Favorito eliminado: Usuario ID {} - Producto ID {}", usuario.getId(), productoId);
    }

    @Override
    public List<FavoritoSalidaDTO> listarFavoritosPorUsuario(String userEmail) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Favorito> favoritos = favoritoRepository.findByUsuarioId(usuario.getId());

        return favoritos.stream()
                .map(favorito -> {
                    FavoritoSalidaDTO dto = new FavoritoSalidaDTO();
                    dto.setId(favorito.getId());
                    dto.setProducto(modelMapper.map(favorito.getProducto(), com.grupo1.pidh.dto.salida.ProductoSalidaDto.class));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean verificarSiEsFavorito(String userEmail, Long productoId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return favoritoRepository.findByUsuarioIdAndProductoId(
                usuario.getId(), productoId).isPresent();
    }
}