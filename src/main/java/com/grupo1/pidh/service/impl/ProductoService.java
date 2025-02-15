package com.grupo1.pidh.service.impl;

import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.service.IProductoService;
import org.modelmapper.ModelMapper;
import org.slf4j.ILoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    private ProductoRepository productoRepository;

    private ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }
    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto dto) {
        Producto producto = modelMapper.map(dto, Producto.class);
        producto = productoRepository.save(producto);
        List<Imagen> imagenes = new ArrayList<>();
        for (int i = 0; i < dto.getImagenes().size(); i++){
           imagenes.add(new Imagen(null, dto.getImagenes().get(i).getRutaImagen(), producto));
        }

        producto.setImagenes(imagenes);
        producto = productoRepository.save(producto);
        System.out.println(producto.getImagenes().get(0).getRutaImagen());
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }
    @Override
    public List<ProductoSalidaDto> listarProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .toList();
    }

    private void configureMapping() {
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.skip(Producto::setImagenes))
                .addMappings(mapper -> mapper.skip(Producto::setCategorias)); //sin crud de categorias

        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getImagenes, ProductoSalidaDto::setImagenesSalidaDto));
    }

    @Override
    public List<ProductoSalidaDto> listarProductosAleatorio(){

        try{
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()){
                return Collections.emptyList();
            }

            //Mezcla aleatoriamente la lista de productos

            Collections.shuffle(productos);
            return productos.stream()
                    .map(producto ->  modelMapper.map(producto, ProductoSalidaDto.class))
                    .toList();
        } catch (Exception e){
            throw new RuntimeException("No se lograron listar los productos en forma aleatoria");
        }
    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) {

        return productoRepository.findById(id)
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .orElse(null);
    }

}
