package com.movies4rent.Servidor.Entities;

import jakarta.persistence.*;

import java.util.List;
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
    private String genero;

    @Column
    private String director;

    @Column
    private Double duracion;

    @Column
    private Double rating;

    @Column
    private Double precio;

    @ManyToMany(mappedBy = "peliculas")
    private List<Usuari> usuaris;


    public Pelicula() {
        super();
    }

    public Pelicula(UUID id, String titulo, String genero, String director, Double rating, Double precio, List<Usuari> usuaris) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.director = director;
        this.rating = rating;
        this.precio = precio;
        this.usuaris = usuaris;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<Usuari> getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(List<Usuari> usuaris) {
        this.usuaris = usuaris;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

}
