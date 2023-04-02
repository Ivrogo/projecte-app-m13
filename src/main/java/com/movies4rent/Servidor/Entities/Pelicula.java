package com.movies4rent.Servidor.Entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String titulo;

    @Column
    private String director;

    @Column
    private String genero;

    @Column
    private int duracion;

    @Column
    private int año;

    @Column
    private int precio;

    public Pelicula() {
    }

    public Pelicula( String titulo, String director, String genero, int duracion, int año, int precio) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.año = año;
        this.precio = precio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }


    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

}
