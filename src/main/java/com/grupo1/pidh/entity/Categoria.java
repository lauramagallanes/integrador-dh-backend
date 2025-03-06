package com.grupo1.pidh.entity;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIAS")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenCategoriaUrl;

    public Categoria(){}

    public Categoria(Long id, String nombre, String descripcion, String imagenCategoriaUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenCategoriaUrl = imagenCategoriaUrl;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenCategoriaUrl() {
        return imagenCategoriaUrl;
    }

    public void setImagenCategoriaUrl(String imagenCategoriaUrl) {
        this.imagenCategoriaUrl = imagenCategoriaUrl;
    }
}
