package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.service.IProductoService;
import com.grupo1.pidh.service.IS3Service;
import com.grupo1.pidh.utils.JacksonConfig;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ObjectMapper objectMapper;

    private final IS3Service s3Service;

    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, ObjectMapper objectMapper, ModelMapper modelMapper, IS3Service s3Service ) {
        this.productoRepository = productoRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.s3Service = s3Service;
        configureMapping();
    }

    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto dto,List<MultipartFile> imagenes) {

        Producto producto = modelMapper.map(dto, Producto.class);
        try {
            LOGGER.info("Producto: {}", objectMapper.writeValueAsString(producto));
        } catch (Exception e) {
            LOGGER.error("Error serializando Producto", e);
        }

        try {
            producto = productoRepository.save(producto);
            LOGGER.info("ProductoRegistrado: {}", objectMapper.writeValueAsString(producto));
        } catch (DataIntegrityViolationException exception) {
            LOGGER.error("Error al guardar el producto en la base de datos", exception);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya exixte un producto con este nombre");
        } catch (Exception e) {
            LOGGER.error("Error serializando ProductoRegistrado", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el producto");
        }


        try {
            List<Imagen> imagenesEntidad = new ArrayList<>();
            for (MultipartFile imagen : imagenes) {
                String imageUrl = s3Service.uploadFile(imagen);
                imagenesEntidad.add(new Imagen(null, imageUrl, producto));
            }

            producto.setImagenes(imagenesEntidad);
            producto = productoRepository.save(producto);
            LOGGER.info("ProductoRegistradoConImagenes: {}", objectMapper.writeValueAsString(producto));
        } catch (Exception e) {
            LOGGER.error("Error al procesar las imágenes", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar las imágenes del producto");
        }

        ProductoSalidaDto productoSalidaDto;
        try {
            productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);
            LOGGER.info("ProductoSalidaDto: {}", objectMapper.writeValueAsString(productoSalidaDto));
        } catch (Exception e) {
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
        try {
            LOGGER.info("Listado de productos: {}", objectMapper.writeValueAsString(productoSalidaDtos));
        } catch (Exception e) {
            LOGGER.error("Error serializando el listado de Productos", e);
        }

        return productoSalidaDtos;
    }

    @Override
    public List<ProductoSalidaDto> listarProductosAleatorio() {

        try {
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                LOGGER.warn("No se encontraron productos en la base de datos.");
                return Collections.emptyList();
            }
            Collections.shuffle(productos);
            List<ProductoSalidaDto> productoSalidaDtosAleatorio = productos.stream()
                    .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                    .toList();


            try {
                LOGGER.info("Listado de productos aleatorio: {}", objectMapper.writeValueAsString(productoSalidaDtosAleatorio));
            } catch (Exception e) {
                LOGGER.error("Error serializando el listado de productos aleatorio", e);
            }
            return productoSalidaDtosAleatorio;
        } catch (Exception e) {
            LOGGER.error("Error al listar los productos en forma aleatoria", e);
            throw new RuntimeException("Ocurrió un error al obtener la lista de productos aleatoria");

        }
    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Id del producto a buscar {}", id);
        ProductoSalidaDto productoSalidaDto = productoRepository.findById(id)
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .orElse(null);
        if (productoSalidaDto == null) {
            LOGGER.error("Producto no encontrado");
            throw new ResourceNotFoundException("El producto solicitado no existe");
        }
        try {
            LOGGER.info("Producto encontrado: {}", objectMapper.writeValueAsString(productoSalidaDto));
        } catch (Exception e) {
            LOGGER.error("Error serializando el producto", e);
        }
        return productoSalidaDto;
    }

    @Override
    public void eliminarProducto(Long id) throws ResourceNotFoundException, ConflictException {
        LOGGER.info("id del prodcuto a eliminar {}", id);
        if (buscarProductoPorId(id) != null) {
            try {
                productoRepository.deleteById(id);
            }catch (DataIntegrityViolationException e){
                LOGGER.error("Producto con informacion relacionada");
                throw new ConflictException("El producto seleccionado no puede ser eliminado ya que tiene información relacionada");
            }catch (Exception e){
                LOGGER.error("Error eliminando el producto", e);
                throw new RuntimeException("Error eliminando el producto");
            }

            LOGGER.warn("Se ha eliminado el prodcuto con id {}", id);
        }


    }



    private void configureMapping() {
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.skip(Producto::setImagenes))
                .addMappings(mapper -> mapper.skip(Producto::setCategorias)); //sin crud de categorias

        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getImagenes, ProductoSalidaDto::setImagenesSalidaDto));
    }
}
