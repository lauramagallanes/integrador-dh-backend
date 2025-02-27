package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.dto.entrada.CategoriaEntradaDto;
import com.grupo1.pidh.dto.salida.CategoriaSalidaDto;
import com.grupo1.pidh.entity.Categoria;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.CategoriaRepository;
import com.grupo1.pidh.service.ICategoriaService;

import com.mysql.cj.log.Log;
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
public class CategoriaService implements ICategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto dto) throws ConflictException {
        Categoria categoria = modelMapper.map(dto, Categoria.class);

        try{
            LOGGER.info("Categoria: {}", objectMapper.writeValueAsString(categoria));
        } catch (Exception e) {
            LOGGER.error("Error serializando categoria", e);
        }

        try{
            categoria = categoriaRepository.save(categoria);
            LOGGER.info("Categoria Registrada: {}", objectMapper.writeValueAsString(categoria));
        }catch (DataIntegrityViolationException exception){
            LOGGER.error("Error al guardar la categoria en la base de datos", exception);
        }catch (Exception e){
            LOGGER.error("Error serializando la Categoria Registrada", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la categoria");
        }

        CategoriaSalidaDto categoriaSalidaDto;
        try{
            categoriaSalidaDto = modelMapper.map(categoria, CategoriaSalidaDto.class);
            LOGGER.info("CategoriaSalidaDto: {}", objectMapper.writeValueAsString(categoriaSalidaDto));
        }catch (Exception e){
            LOGGER.error("Error serializando CategoriaSalidaDto", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al mapear CategoriaSalidaDto");

        }
        return categoriaSalidaDto;
    }

    @Override
    public List<CategoriaSalidaDto> listarCategorias() {

        List<CategoriaSalidaDto> categoriasSalidaDto = categoriaRepository.findAll()
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaSalidaDto.class))
                .toList();

        try{
            LOGGER.info("Listado de categorias: {}", objectMapper.writeValueAsString(categoriasSalidaDto));
        }catch (Exception e){
            LOGGER.error("Error serializando el listado de categorias", e);
        }

        return categoriasSalidaDto;
    }

    @Override
    public CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Id de la categoria a buscar: {}", id);

        CategoriaSalidaDto categoriaSalidaDto = categoriaRepository.findById(id)
                .map(categoria -> modelMapper.map(categoria, CategoriaSalidaDto.class))
                .orElse(null);

        if (categoriaSalidaDto == null){
            LOGGER.error("Categoria no encontrada");
            throw new ResourceNotFoundException("La categoria solicitada no existe");
        }

        try{
            LOGGER.info("Categoria encontrada: {}", objectMapper.writeValueAsString(categoriaSalidaDto));
        }catch (Exception e){
            LOGGER.error("Error serializando la categotría", e);

        }

        return categoriaSalidaDto;
    }

    @Override
    public void eliminarCategoria(Long id) throws ResourceNotFoundException, ConflictException {
        LOGGER.info("Id de la categoria a eliminar: {}", id);

        if (buscarCategoriaPorId(id) != null){
            try{
                categoriaRepository.deleteById(id);
            } catch (DataIntegrityViolationException e){
                LOGGER.error("Categoria con informacion relacionada");
                throw new ConflictException("La categoria seleccionada no puede ser eliminada ya que esta siendo usada por uno o mas productos");
            } catch (Exception e){
                LOGGER.error("Error eliminando la categoria", e);
                throw new RuntimeException("Error eliminando la categoria");
            }

            LOGGER.warn("Se ha eliminado la categoria con id {}", id);
        }
    }
}
