package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.dto.entrada.ProductoImagenEntradaDto;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.entity.ProductoImagen;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.grupo1.pidh.service.IS3Service;

import static com.grupo1.pidh.utils.enums.TipoEvento.FECHA_UNICA;
import static com.grupo1.pidh.utils.enums.TipoTarifa.POR_PERSONA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-local.properties")
class ProductoServiceTest {
    private final ProductoRepository productoRepositoryMock = mock(ProductoRepository.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final IS3Service s3ServiceMock = mock(IS3Service.class);

    @Autowired //inyecto el ObjectMapper configurado en JacksonConfig
    private ObjectMapper objectMapper;
    private ProductoService productoService;
    private static ProductoEntradaDto productoEntradaDto;
    private static Producto producto;
    private static LocalTime horaInicio = LocalTime.of(18, 30, 0);
    private static LocalTime horaFin = LocalTime.of(21, 30, 0);
    private static LocalDate diaEvento = LocalDate.of(2025, 6, 21);
    private static List<ProductoImagen> productoImagenes;
    private static List<ProductoImagenEntradaDto> productoImagenEntradaDtos;
    private static List<MultipartFile> multipartFiles;

    @BeforeAll
    static void setUp(){
        producto = new Producto(1L, "Observacion de cielo nocturno", "Una noche para disfrutar", 500.00, POR_PERSONA, "Español", horaInicio, horaFin, FECHA_UNICA, diaEvento, Collections.emptyList(), Collections.emptySet(), Collections.emptyList());
        productoImagenes = List.of(
                new ProductoImagen(1L, "https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN1434.JPG", producto),
                new ProductoImagen(2L, "https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN0710.JPG", producto)
        );
        multipartFiles = List.of(
                mock(MultipartFile.class),
                mock(MultipartFile.class)
        );
        productoImagenEntradaDtos = List.of(
                new ProductoImagenEntradaDto("https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN1434.JPG"),
                new ProductoImagenEntradaDto("https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN0710.JPG"));

        producto.setProductoImagenes(productoImagenes);
        productoEntradaDto = new ProductoEntradaDto("Observacion de cielo nocturno", "Una noche para disfrutar", 500.00, POR_PERSONA, "Español", horaInicio, horaFin, FECHA_UNICA, Collections.emptyList(), diaEvento, Collections.emptySet(), productoImagenEntradaDtos);
    }

    @BeforeEach
    void initService(){
        productoService = new ProductoService(productoRepositoryMock, objectMapper, modelMapper, s3ServiceMock);
        when(s3ServiceMock.uploadFile(any(MultipartFile.class)))
                .thenReturn("https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN1434.JPG")
                .thenReturn("https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN0710.JPG");
    }


    @Test
    void deberiaMandarAlRepositoryUnProductoDeNombreObservacionDeCieloNocturno_yRetornarUnaSalidaDtoConSuId(){
        when(productoRepositoryMock.save(any(Producto.class))).thenReturn(producto);
        ProductoSalidaDto productoSalidaDto = productoService.registrarProducto(productoEntradaDto,multipartFiles);

        assertNotNull(productoSalidaDto);
        assertNotNull(productoSalidaDto.getId());
        assertEquals("Observacion de cielo nocturno", productoSalidaDto.getNombre());
        verify(productoRepositoryMock, times(2)).save(any(Producto.class));
        verify(s3ServiceMock, times(2)).uploadFile(any(MultipartFile.class));

    }

    @Test
    void deberiaDevolverUnListadoNoVacioDeProductos(){
        List<Producto> productos = new ArrayList<>(List.of(producto));
        when(productoRepositoryMock.findAll()).thenReturn(productos);

        List<ProductoSalidaDto> listadoProductos = productoService.listarProductos();
        assertFalse(listadoProductos.isEmpty());
    }

    @Test
    void deberiaLanzarExcepcionCuandoProductoDuplicado() {
        when(productoRepositoryMock.save(any(Producto.class))).thenThrow(new DataIntegrityViolationException("Duplicado"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productoService.registrarProducto(productoEntradaDto, multipartFiles);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void deberiaEliminarProductoSiExiste() {
        when(productoRepositoryMock.findById(1L)).thenReturn(java.util.Optional.of(producto));

        assertDoesNotThrow(() -> productoService.eliminarProducto(1L));
        verify(productoRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoProductoAEliminarNoExiste() {
        when(productoRepositoryMock.findById(99L)).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productoService.eliminarProducto(99L);
        });

        assertEquals("El producto solicitado no existe", exception.getMessage());

        verify(productoRepositoryMock, never()).deleteById(anyLong());
    }
}