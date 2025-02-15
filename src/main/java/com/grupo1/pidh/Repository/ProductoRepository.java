package com.grupo1.pidh.Repository;

import com.grupo1.pidh.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
