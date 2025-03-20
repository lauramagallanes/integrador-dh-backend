package com.grupo1.pidh.repository;

import com.grupo1.pidh.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuarioId(Long usuarioId);

    @Query("SELECT pf FROM Favorito pf WHERE pf.usuario.id = :usuarioId AND pf.producto.id = :productoId")
    Optional<Favorito> findByUsuarioIdAndProductoId(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);

    void deleteByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
}