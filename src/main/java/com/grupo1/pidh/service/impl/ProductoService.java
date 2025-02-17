package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.service.IProductoService;
import com.grupo1.pidh.utils.JacksonConfig;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ObjectMapper objectMapper;



    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        configureMapping();
    }
    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto dto) {

        Producto producto = modelMapper.map(dto, Producto.class);
        try{
            LOGGER.info("Producto: {}", objectMapper.writeValueAsString(producto));
        }catch (Exception e) {
            LOGGER.error("Error serializando Producto", e);
        }

        try{
            producto = productoRepository.save(producto);
            LOGGER.info("ProductoRegistrado: {}", objectMapper.writeValueAsString(producto));
        }  catch (DataIntegrityViolationException exception) {
            LOGGER.error("Error al guardar el producto en la base de datos", exception);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya exixte un producto con este nombre");
        } catch (Exception e) {
            LOGGER.error("Error serializando ProductoRegistrado", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el producto");
        }


        try{
            List<Imagen> imagenes = new ArrayList<>();
            for (int i = 0; i < dto.getImagenes().size(); i++){
                imagenes.add(new Imagen(null, dto.getImagenes().get(i).getRutaImagen(), producto));
            }

            producto.setImagenes(imagenes);
            producto = productoRepository.save(producto);
            LOGGER.info("ProductoRegistradoConImagenes: {}", objectMapper.writeValueAsString(producto));
        }catch (Exception e) {
            LOGGER.error("Error serializando ProductoRegistradoConImagenes", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al asociar las imagenes al producto");
        }

        ProductoSalidaDto productoSalidaDto;
        try{
            productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);
            LOGGER.info("ProductoSalidaDto: {}", objectMapper.writeValueAsString(productoSalidaDto));
        }catch (Exception e) {
            LOGGER.error("Error serializando ProductoSalidaDto", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al mapear ProductoSalidaDto");
        }

        return productoSalidaDto;
    }
    @Override
    public List<ProductoSalidaDto> listarProductos() {
        List<ProductoSalidaDto> productoSalidaDtos = productoRepository.findAll()
                .stream()
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .toList();
        try{
            LOGGER.info("Listado de productos: {}", objectMapper.writeValueAsString(productoSalidaDtos));
        }catch (Exception e) {
            LOGGER.error("Error serializando el listado de Productos", e);
        }

        return productoSalidaDtos;
    }

    @Override
    public List<ProductoSalidaDto> listarProductosAleatorio(){

        try{
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()){
                LOGGER.warn("No se encontraron productos en la base de datos.");
                return Collections.emptyList();
            }
            Collections.shuffle(productos);
            List<ProductoSalidaDto> productoSalidaDtosAleatorio = productos.stream()
                    .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                    .toList();


            try{
                LOGGER.info("Listado de productos aleatorio: {}", objectMapper.writeValueAsString(productoSalidaDtosAleatorio));
            }catch (Exception e) {
                LOGGER.error("Error serializando el listado de productos aleatorio", e);
            }
            return productoSalidaDtosAleatorio;
        } catch (Exception e){
            LOGGER.error("Error al listar los productos en forma aleatoria", e);
            throw new RuntimeException("Ocurrió un error al obtener la lista de productos aleatoria");

        }
    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException {
        ProductoSalidaDto productoSalidaDto = productoRepository.findById(id)
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .orElse(null);
        if (productoSalidaDto == null){
            throw new ResourceNotFoundException("El producto solicitado no existe");
        }


        return productoSalidaDto;
    }


    private void configureMapping() {
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.skip(Producto::setImagenes))
                .addMappings(mapper -> mapper.skip(Producto::setCategorias)); //sin crud de categorias

        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getImagenes, ProductoSalidaDto::setImagenesSalidaDto));
    }
}
