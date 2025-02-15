package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
