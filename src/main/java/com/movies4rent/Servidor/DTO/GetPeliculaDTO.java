package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class GetPeliculaDTO {

    private UUID id;

    private String titulo;

    private String genero;

    private String director;

    private int duracion;

    private LocalDate año;

    private BigDecimal precio;

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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
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


    public static GetPeliculaDTO fromEntityToDTO(Pelicula pelicula) {
        GetPeliculaDTO getPeliculaDTO = new GetPeliculaDTO();

        getPeliculaDTO.setId(pelicula.getId());
        getPeliculaDTO.setTitulo(pelicula.getTitulo());
        getPeliculaDTO.setDirector(pelicula.getDirector());
        getPeliculaDTO.setGenero(pelicula.getGenero());
        getPeliculaDTO.setDuracion(pelicula.getDuracion());
        getPeliculaDTO.setAño(pelicula.getAño());
        getPeliculaDTO.setPrecio(pelicula.getPrecio());

        return getPeliculaDTO;
    }
}
