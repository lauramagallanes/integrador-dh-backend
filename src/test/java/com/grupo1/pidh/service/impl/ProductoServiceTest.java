package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo1.pidh.dto.entrada.ImagenEntradaDto;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.entity.Imagen;
import com.grupo1.pidh.entity.Producto;
import com.grupo1.pidh.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.grupo1.pidh.utils.enums.TipoEvento.FECHA_UNICA;
import static com.grupo1.pidh.utils.enums.TipoTarifa.POR_PERSONA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductoServiceTest {
    private final ProductoRepository productoRepositoryMock = mock(ProductoRepository.class);
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired //inyecto el ObjectMapper configurado en JacksonConfig
    private ObjectMapper objectMapper;
    private ProductoService productoService;
    private static ProductoEntradaDto productoEntradaDto;
    private static Producto producto;
    private static LocalTime horaInicio = LocalTime.of(18, 30, 0);
    private static LocalTime horaFin = LocalTime.of(21, 30, 0);
    private static LocalDate diaEvento = LocalDate.of(2025, 6, 21);
    private static List<Imagen> imagenes;
    private static List<ImagenEntradaDto> imagenEntradaDtos;

    @BeforeAll
    static void setUp(){
        producto = new Producto(1L, "Observacion de cielo nocturno", "Una noche para disfrutar", 500.00, POR_PERSONA, "Español", horaInicio, horaFin, FECHA_UNICA, diaEvento, Collections.emptyList(), Collections.emptySet(), Collections.emptyList());
        imagenes = List.of(
                new Imagen(1L, "https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN1434.JPG", producto),
                new Imagen(2L, "https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN0710.JPG", producto)
        );

        imagenEntradaDtos = List.of(
                new ImagenEntradaDto("https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN1434.JPG"),
                new ImagenEntradaDto("https://imagenespasocenturion.s3.us-east-1.amazonaws.com/DSCN0710.JPG"));

        producto.setImagenes(imagenes);
        productoEntradaDto = new ProductoEntradaDto("Observacion de cielo nocturno", "Una noche para disfrutar", 500.00, POR_PERSONA, "Español", horaInicio, horaFin, FECHA_UNICA, Collections.emptyList(), diaEvento, Collections.emptySet(), imagenEntradaDtos );
    }

    @BeforeEach
    void initService(){
        productoService = new ProductoService(productoRepositoryMock, objectMapper, modelMapper);
    }

    @Test
    void deberiaMandarAlRepositoryUnProductoDeNombreObservacionDeCieloNocturno_yRetornarUnaSalidaDtoConSuId(){
        when(productoRepositoryMock.save(any(Producto.class))).thenReturn(producto);
        ProductoSalidaDto productoSalidaDto = productoService.registrarProducto(productoEntradaDto);

        assertNotNull(productoSalidaDto);
        assertNotNull(productoSalidaDto.getId());
        assertEquals("Observacion de cielo nocturno", productoSalidaDto.getNombre());
        verify(productoRepositoryMock, times(2)).save(any(Producto.class));
    }

    @Test
    void deberiaDevolverUnListadoNoVacioDeProductos(){
        List<Producto> productos = new ArrayList<>(List.of(producto));
        when(productoRepositoryMock.findAll()).thenReturn(productos);

        List<ProductoSalidaDto> listadoProductos = productoService.listarProductos();
        assertFalse(listadoProductos.isEmpty());
    }
}