package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva, Long> {
    @Query(value = "SELECT r FROM Reserva r WHERE r.usuario.email = :usuarioEmail ORDER BY r.id DESC")
    List<Reserva> findByUsuarioEmailOrderByIdDesc(String usuarioEmail);

    @Query("SELECT r FROM Reserva r WHERE r.disponibilidadProducto.producto.id = :productoId AND r.puntuacion IS NOT NULL")
    List<Reserva> findResenasByProductoId(@Param("productoId") Long productoId);

    List<Reserva> findByUsuarioEmailAndProductoId(String usuarioEmail, Long productoId);
}
