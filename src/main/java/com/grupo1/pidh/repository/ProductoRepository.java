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
            "AND (:fechaInicio IS NULL OR d.fechaEvento >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR d.fechaEvento <= :fechaFin) " +
            "AND (:categoriaNombre IS NULL OR LOWER(c.nombre) = LOWER(:categoriaNombre))")
    List<Producto> buscarPorFiltros(
            @Param("nombre") String nombre,
            @Param("fechaInicio")LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            @Param("categoriaNombre") String categoriaNombre
            );
}
