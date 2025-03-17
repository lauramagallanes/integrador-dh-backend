package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva, Long> {
}
