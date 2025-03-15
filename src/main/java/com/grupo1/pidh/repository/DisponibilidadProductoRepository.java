package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.DisponibilidadProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadProductoRepository extends JpaRepository<DisponibilidadProducto, Long> {
    List<DisponibilidadProducto> findByProductoId(Long productoId);
}
