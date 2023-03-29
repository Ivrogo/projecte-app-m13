package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PeliculaUpdateDTO {

    private String titulo;

    private String director;

    private String genero;

    private int duracion;

    private LocalDate año;

    private BigDecimal precio;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public static void fromDTOToEntityUpdate (PeliculaUpdateDTO peliculaUpdateDTO, Pelicula pelicula) {

        pelicula.setTitulo(peliculaUpdateDTO.getTitulo());
        pelicula.setDirector(peliculaUpdateDTO.getDirector());
        pelicula.setGenero(peliculaUpdateDTO.getGenero());
        pelicula.setDuracion(peliculaUpdateDTO.getDuracion());
        pelicula.setPrecio(peliculaUpdateDTO.getPrecio());
    }
}
