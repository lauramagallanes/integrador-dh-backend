package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT DISTINCT p FROM Producto p " +
            "LEFT JOIN p.disponibilidad d " +
            "LEFT JOIN p.categorias c " +
            "WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:fechaEvento IS NULL OR d.fechaEvento >= :fechaEvento) " +
            "AND (:categoriaId IS NULL OR c.id = :categoriaId)")
    List<Producto> buscarPorFiltros(
            @Param("nombre") String nombre,
            @Param("fechaEvento")LocalDate fechaEvento,
            @Param("categoriaId") Long categoriaId
            );
}
