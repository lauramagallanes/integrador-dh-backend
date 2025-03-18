package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r WHERE r.usuario.email = :usuarioEmail ORDER BY r.id DESC")
    Reserva findTopByUsuarioEmailOrderByIdDesc(String usuarioEmail);
}
