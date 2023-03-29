package com.movies4rent.Servidor.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private int duracion;

    @Column
    private LocalDate año;

    @Column
    private BigDecimal precio;

    @ManyToMany(mappedBy = "peliculas")
    private List<Usuari> usuaris;



    public Pelicula() {
        super();
    }

    public Pelicula(UUID id, String titulo, String genero, String director, int duración, LocalDate año, BigDecimal precio, List<Usuari> usuaris) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.director = director;
        this.duracion = duracion;
        this.año = año;
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

    public LocalDate getAño() {
        return año;
    }

    public void setAño(LocalDate año) {
        this.año = año;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public List<Usuari> getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(List<Usuari> usuaris) {
        this.usuaris = usuaris;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

}
