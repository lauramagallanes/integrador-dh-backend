package com.grupo1.pidh.entity;

import javax.persistence.*;

@Entity
@Table(name = "TIPOS_EVENTO")
public class TipoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "nombre")
    private String nombre;

    public TipoEvento(){}

    public TipoEvento(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
