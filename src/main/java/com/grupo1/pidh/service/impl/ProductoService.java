package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.dto.entrada.FiltroProductoEntradaDto;
import com.grupo1.pidh.dto.salida.DisponibilidadProductoSalidaDto;
import com.grupo1.pidh.entity.*;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ConflictException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.CaracteristicaRepository;
import com.grupo1.pidh.repository.CategoriaRepository;
import com.grupo1.pidh.repository.FavoritoRepository;
import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.service.IProductoService;
import com.grupo1.pidh.service.IS3Service;
import com.grupo1.pidh.utils.enums.DiaSemana;
import com.grupo1.pidh.utils.enums.TipoEvento;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ObjectMapper objectMapper;

    private final IS3Service s3Service;

    private final ModelMapper modelMapper;

    private final CategoriaRepository categoriaRepository;
    private final CaracteristicaRepository caracteristicaRepository;
    private final FavoritoRepository favoritoRepository;

    public ProductoService(ProductoRepository productoRepository, ObjectMapper objectMapper, IS3Service s3Service, ModelMapper modelMapper, CategoriaRepository categoriaRepository, CaracteristicaRepository caracteristicaRepository, FavoritoRepository favoritoRepository) {
        this.productoRepository = productoRepository;
        this.objectMapper = objectMapper;
        this.s3Service = s3Service;
        this.modelMapper = modelMapper;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.favoritoRepository = favoritoRepository;
        configureMapping();
    }

    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto dto,List<MultipartFile> imagenes) throws BadRequestException {

        if (imagenes.size() < 5) {
            LOGGER.error("Error: hay menos de 5 imagenes para el producto");
            throw new BadRequestException("Debe ingresar al menos 5 imagenes.");
        }


        Producto producto = modelMapper.map(dto, Producto.class);
        try {
            LOGGER.info("Producto: {}", objectMapper.writeValueAsString(producto));
        } catch (Exception e) {
            LOGGER.error("Error serializando Producto", e);
        }

        Set<Categoria> categorias = new HashSet<>();

        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()){
            for (Long categoriaId: dto.getCategoriasIds()){
                Categoria categoria = categoriaRepository.findById(categoriaId)
                        .orElseThrow(()-> new ResourceNotFoundException("Categoria no encontrada"));
                if (!categoria.isActivo()){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría " + categoria.getNombre() + " no está activa." );
                }
                categorias.add(categoria);
            }
        }
        producto.setCategorias(categorias);

        Set<Caracteristica> caracteristicas = new HashSet<>();
        if (dto.getCaracteristicasIds() != null && !dto.getCaracteristicasIds().isEmpty()){
            for (Long caracteristicaId: dto.getCaracteristicasIds()){
                Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                        .orElseThrow(()-> new ResourceNotFoundException("Caracteristica no encontrada"));
                caracteristicas.add(caracteristica);
            }
        }
        producto.setCaracteristicas(caracteristicas);

        List<DisponibilidadProducto> disponibilidad = generarDisponibilidad(producto, dto.getTipoEvento(), dto.getFechaEvento(), dto.getFechaFinEvento(), dto.getDiasDisponible(), dto.getCuposTotales());

        producto.setDisponibilidad(disponibilidad);

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
            List<ProductoImagen> productoImagenesEntidad = new ArrayList<>();
            for (MultipartFile imagen : imagenes) {
                String imageUrl = s3Service.uploadFile(imagen);
                productoImagenesEntidad.add(new ProductoImagen(null, imageUrl, producto));
            }

            producto.setProductoImagenes(productoImagenesEntidad);
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
            List<Producto> productos = productoRepository.buscarPorFiltros(null, LocalDate.now(), null, null);
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
                List<Favorito> favoritoList = favoritoRepository.findByProductoId(id).stream().toList();
                for (Favorito favorito : favoritoList) {
                    favoritoRepository.delete(favorito);
                }
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

    @Override
    public ProductoSalidaDto editarProducto(Long id, ProductoEntradaDto dto, List<MultipartFile> imagenes) throws ResourceNotFoundException, ConflictException, BadRequestException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));

        Hibernate.initialize(producto.getCategorias());
        Hibernate.initialize(producto.getCaracteristicas());
        Hibernate.initialize((producto.getProductoImagenes()));
        Hibernate.initialize(producto.getDisponibilidad());

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setValorTarifa(dto.getValorTarifa());
        producto.setTipoTarifa(dto.getTipoTarifa());
        producto.setIdioma(dto.getIdioma());
        producto.setHoraInicio(dto.getHoraInicio());
        producto.setHoraFin(dto.getHoraFin());
        producto.setTipoEvento(dto.getTipoEvento());
        producto.setPais(dto.getPais());
        producto.setCiudad(dto.getCiudad());
        producto.setDireccion(dto.getDireccion());
        producto.setPoliticaCancelacion(dto.getPoliticaCancelacion());
        producto.setPoliticaPagos(dto.getPoliticaPagos());
        producto.setTelefono(dto.getTelefono());

        if (dto.getCategoriasIds() != null) { // Permite dejar el producto sin categorías si se envía vacío
            Set<Categoria> nuevasCategorias = new HashSet<>();
            for (Long categoriaId : dto.getCategoriasIds()) {
                Categoria categoria = categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + categoriaId));
                if (!categoria.isActivo() && !producto.getCategorias().contains(categoria)){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoría " + categoria.getNombre() + " no está activa." );
                }
                nuevasCategorias.add(categoria);
            }
            if (!nuevasCategorias.isEmpty()){
                producto.setCategorias(nuevasCategorias);
            }

        }

        if (dto.getCaracteristicasIds() != null){
            Set<Caracteristica> nuevasCaracteristicas = new HashSet<>();
            for (Long caracteristicaId: dto.getCaracteristicasIds()){
                Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                        .orElseThrow(()-> new ResourceNotFoundException("Caracteristica no encontrada con ID: " + caracteristicaId));
                nuevasCaracteristicas.add(caracteristica);
            }
            if (!nuevasCaracteristicas.isEmpty()){
                producto.setCaracteristicas(nuevasCaracteristicas);
            }
        }

        //obtengo las disponibilidades
        List<DisponibilidadProducto> disponibilidadActual = producto.getDisponibilidad();

        //obtengo los nuevos dias validos
        LocalDate fechaActual = dto.getFechaEvento();
        List<LocalDate> diasDisponibles = new ArrayList<>();
        if (dto.getTipoEvento() == TipoEvento.FECHA_UNICA){
            diasDisponibles.add(fechaActual);
        }else {
            LocalDate fechaFin = dto.getFechaFinEvento();
            if (dto.getDiasDisponible() == null){
                throw new BadRequestException("Para eventos de fecha recurrente debe agregar al menos un día de Lunes a Domingo");
            }
            while (!fechaActual.isAfter(fechaFin)) {
                String diaEnEspanol = traducirDiaSemana(fechaActual.getDayOfWeek());
                if (dto.getDiasDisponible().contains(DiaSemana.valueOf(diaEnEspanol))) {
                    diasDisponibles.add(fechaActual);
                }
                fechaActual = fechaActual.plusDays(1);
            }
        }
        //busco si hay dias reservados que no estarán en el nuevo producto
        List<DisponibilidadProducto> disponibildiadesNoBorrables = disponibilidadActual.stream()
                .filter(d -> (!diasDisponibles.contains(d.getFechaEvento()) && d.getCuposReservados() != 0)).toList();

       if (!disponibildiadesNoBorrables.isEmpty()){
           throw new ConflictException("Hay reservas confirmadas para el "+ disponibildiadesNoBorrables.get(0).getFechaEvento().toString());
       }

       //busco si hay dias reservados que tengan mas reservas que el nuevo cupo
       disponibildiadesNoBorrables = disponibilidadActual.stream()
               .filter(d -> d.getCuposReservados() > dto.getCuposTotales()).toList();
       if (!disponibildiadesNoBorrables.isEmpty()){
           throw new ConflictException("Hay más de "+ dto.getCuposTotales() +" reservas confirmadas para el "+ disponibildiadesNoBorrables.get(0).getFechaEvento().toString());
       }

       //elimino las reservas borrables
       disponibilidadActual.removeIf(d -> !diasDisponibles.contains(d.getFechaEvento()) && d.getCuposReservados() == 0);

       //agrego o modifico las disponibilidades
       for (LocalDate fecha : diasDisponibles){
           List<DisponibilidadProducto> disponibilidades = disponibilidadActual.stream().filter(d -> d.getFechaEvento().isEqual(fecha)).toList();
           if (disponibilidades.isEmpty()){
               disponibilidadActual.add(new DisponibilidadProducto(null, producto, fecha, dto.getCuposTotales(), 0, null));
           }else{
               for (DisponibilidadProducto d : disponibilidades){
                   d.setCuposTotales(dto.getCuposTotales());
               }
           }
       }

       try {
            producto = productoRepository.save(producto);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error al actualizar el producto", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con este nombre");
        }


        try {
            LOGGER.info("Producto actualizado: {}", objectMapper.writeValueAsString(producto));
        } catch (Exception e) {
            LOGGER.error("Error serializando el producto actualizado", e);
        }


        if (imagenes != null && !imagenes.isEmpty()) {
            List<ProductoImagen> nuevasImagenes = new ArrayList<>();
            for (MultipartFile imagen : imagenes) {
                String imageUrl = s3Service.uploadFile(imagen);
                nuevasImagenes.add(new ProductoImagen(null, imageUrl, producto));
            }
            producto.getProductoImagenes().addAll(nuevasImagenes);
            productoRepository.save(producto);
            LOGGER.info("Nuevas imagenes agregadas al producto");
        }

        return modelMapper.map(producto, ProductoSalidaDto.class);
    }

    @Override
    public List<ProductoSalidaDto> buscarProductosPorFiltros(FiltroProductoEntradaDto filtros) {
        List<Producto> productos = productoRepository.buscarPorFiltros(
                filtros.getNombre(),
                filtros.getFechaInicio(),
                filtros.getFechaFin(),
                filtros.getCategoriaNombre()
        );
        return productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductoSalidaDto> filtrarProductosPorNombre(String query){
        List<Producto> productosFiltrados = productoRepository.findByNombreContainingIgnoreCase(query);
        return productosFiltrados.stream()
                .map(producto -> modelMapper.map(producto, ProductoSalidaDto.class))
                .collect(Collectors.toList());
    }

    private List<DisponibilidadProducto> generarDisponibilidad(Producto producto, TipoEvento tipoevento, LocalDate fechaInicio, LocalDate fechaFin, List<DiaSemana> diasDisponibles, int cuposTotales) {
        List<DisponibilidadProducto> disponibilidadList = new ArrayList<>();

        if (tipoevento == TipoEvento.FECHA_UNICA){
            disponibilidadList.add(new DisponibilidadProducto(null, producto, fechaInicio, cuposTotales, 0, null));
        } else if (tipoevento == TipoEvento.RECURRENTE) {
            LocalDate fechaActual = fechaInicio;
            while (!fechaActual.isAfter(fechaFin)){
                String diaEnEspanol = traducirDiaSemana(fechaActual.getDayOfWeek());

                if (diasDisponibles.contains(DiaSemana.valueOf(diaEnEspanol))){
                    disponibilidadList.add(new DisponibilidadProducto(null, producto, fechaActual, cuposTotales, 0, null));
                }
                fechaActual=fechaActual.plusDays(1);
            }
        }

        return disponibilidadList;
    }

    private String traducirDiaSemana(DayOfWeek dayOfWeek){
        switch (dayOfWeek){
            case MONDAY: return "LUNES";
            case TUESDAY: return "MARTES";
            case WEDNESDAY: return "MIERCOLES";
            case THURSDAY: return "JUEVES";
            case FRIDAY: return "VIERNES";
            case SATURDAY: return "SABADO";
            case SUNDAY: return "DOMINGO";
            default: throw new IllegalArgumentException("Día de la semana desconocido: " + dayOfWeek);
        }
    }

    private void configureMapping() {
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.skip(Producto::setProductoImagenes))
                .addMappings(mapper -> mapper.skip(Producto::setCategorias)); //sin crud de categorias

        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getProductoImagenes, ProductoSalidaDto::setProductoImagenesSalidaDto));

        modelMapper.typeMap(DisponibilidadProducto.class, DisponibilidadProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getProducto().getId(), DisponibilidadProductoSalidaDto::setProductoId));
    }
}
