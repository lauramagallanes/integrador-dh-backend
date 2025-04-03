package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT DISTINCT c FROM Categoria c " +
            "JOIN c.productos p " +
            "JOIN p.disponibilidad d " +
            "WHERE d.fechaEvento >= :fecha")
    List<Categoria> findDistinctCategoriasByFechaDisponibilidad(@Param("fecha") LocalDate fecha);

}
