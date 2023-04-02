package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

import java.util.UUID;

public class GetPeliculaDTO {

    private UUID id;

    private String titulo;

    private String director;

    private String genero;


    private int duracion;

    private int año;

    private int precio;

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
