package com.grupo1.pidh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grupo1.pidh.dto.entrada.ProductoEntradaDto;
import com.grupo1.pidh.dto.salida.ProductoSalidaDto;
import com.grupo1.pidh.entity.*;
import com.grupo1.pidh.exceptions.BadRequestException;
import com.grupo1.pidh.exceptions.ResourceNotFoundException;
import com.grupo1.pidh.repository.CaracteristicaRepository;
import com.grupo1.pidh.repository.CategoriaRepository;
import com.grupo1.pidh.repository.FavoritoRepository;
import com.grupo1.pidh.repository.ProductoRepository;
import com.grupo1.pidh.utils.enums.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.grupo1.pidh.service.IS3Service;

import static com.grupo1.pidh.utils.enums.TipoTarifa.POR_PERSONA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-local.properties")
class ProductoServiceTest {
    private final ProductoRepository productoRepositoryMock = mock(ProductoRepository.class);
    private final CategoriaRepository categoriaRepositoryMock = mock(CategoriaRepository.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final IS3Service s3ServiceMock = mock(IS3Service.class);
    private final CaracteristicaRepository caracteristicaRepositoryMock = mock(CaracteristicaRepository.class);
    private final FavoritoRepository favoritoRepositoryMock = mock(FavoritoRepository.class);

    private ObjectMapper objectMapper;
    private ProductoService productoService;
    private static ProductoEntradaDto productoEntradaDto;
    private static Producto producto;
    private static LocalTime horaInicio = LocalTime.of(18, 30, 0);
    private static LocalTime horaFin = LocalTime.of(21, 30, 0);
    private static LocalDate fechaEvento = LocalDate.of(2025, 6, 21);
    private static LocalDate fechaFinEvento = LocalDate.of(2025, 6, 30);
    private static List<MultipartFile> multipartFiles;
    private static Set<Long> categoriasIds;
    private static Set<Categoria> categorias;
    private static Set<Long> caracteristicasIds;
    private static Set<Caracteristica> caracteristicas;

    @BeforeAll
    static void setUp() {
        // 🔹 Crear disponibilidad para el producto
        List<DisponibilidadProducto> disponibilidad = new ArrayList<>();
        disponibilidad.add(new DisponibilidadProducto(null, fechaEvento, 20));

        producto = new Producto(
                1L, "Observación de cielo nocturno", "Experiencia única", 500.00, POR_PERSONA,
                "Español", horaInicio, horaFin, TipoEvento.FECHA_UNICA, disponibilidad,
                Collections.emptyList(), new HashSet<>(), new HashSet<>(), Collections.emptyList(),
                "Uruguay", "Montevideo", "Av. 18 de Julio 1234", PoliticaCancelacion.FLEXIBLE, PoliticaPagos.PAGO_TOTAL_ANTICIPADO, "+59898372742"
        );

        multipartFiles = List.of(
                mock(MultipartFile.class),
                mock(MultipartFile.class),
                mock(MultipartFile.class),
                mock(MultipartFile.class),
                mock(MultipartFile.class)
        );

        categoriasIds = new HashSet<>(Arrays.asList(1L, 2L));
        categorias = new HashSet<>(Arrays.asList(
                new Categoria(1L, "Astroturismo", "Experiencia nocturna", null, true),
                new Categoria(2L, "Naturaleza", "Observación de flora y fauna", null, true)
        ));

        caracteristicasIds = new HashSet<>(Arrays.asList(1L, 3L));
        caracteristicas = new HashSet<>(Arrays.asList(
                new Caracteristica(1L, "Binoculares", "icono1.png"),
                new Caracteristica(3L, "Guía especializado", "icono3.png")
        ));

        productoEntradaDto = new ProductoEntradaDto(
                "Observación de cielo nocturno", "Experiencia única", 500.00, POR_PERSONA,
                "Español", horaInicio, horaFin, TipoEvento.FECHA_UNICA, Collections.emptyList(),
                fechaEvento, fechaFinEvento, categoriasIds, caracteristicasIds, null,
                "Uruguay", "Montevideo", "Av. 18 de Julio 1234", PoliticaCancelacion.FLEXIBLE, PoliticaPagos.PAGO_TOTAL_ANTICIPADO, 20, "+59898372742"
        );
    }

    @BeforeEach
    void initService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        productoService = new ProductoService(productoRepositoryMock, objectMapper, s3ServiceMock, modelMapper, categoriaRepositoryMock, caracteristicaRepositoryMock, favoritoRepositoryMock);

        when(categoriaRepositoryMock.findById(anyLong()))
                .thenAnswer(invocation -> categorias.stream()
                        .filter(c -> c.getId().equals(invocation.getArgument(0)))
                        .findFirst());

        when(caracteristicaRepositoryMock.findById(anyLong()))
                .thenAnswer(invocation -> caracteristicas.stream()
                        .filter(c -> c.getId().equals(invocation.getArgument(0)))
                        .findFirst());
    }

    @Test
    void deberiaRegistrarProductoYGenerarDisponibilidades() throws BadRequestException {
        when(productoRepositoryMock.save(any(Producto.class))).thenReturn(producto);

        ProductoSalidaDto productoSalidaDto = productoService.registrarProducto(productoEntradaDto, multipartFiles);

        assertNotNull(productoSalidaDto);
        assertNotNull(productoSalidaDto.getId());
        assertEquals("Observación de cielo nocturno", productoSalidaDto.getNombre());

        verify(productoRepositoryMock, times(2)).save(any(Producto.class));
        verify(s3ServiceMock, times(5)).uploadFile(any(MultipartFile.class));
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
        when(productoRepositoryMock.findById(1L)).thenReturn(Optional.of(producto));

        assertDoesNotThrow(() -> productoService.eliminarProducto(1L));
        verify(productoRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoProductoAEliminarNoExiste() {
        when(productoRepositoryMock.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productoService.eliminarProducto(99L);
        });

        assertEquals("El producto solicitado no existe", exception.getMessage());

        verify(productoRepositoryMock, never()).deleteById(anyLong());
    }
}