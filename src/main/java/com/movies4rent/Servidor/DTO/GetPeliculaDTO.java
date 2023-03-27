package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

import java.util.UUID;

public class GetPeliculaDTO {

    private UUID id;

    private String titulo;

    private String genero;

    private String director;

    private Double duracion;

    private Double rating;

    private Double precio;

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

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
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


    public static GetPeliculaDTO fromEntityToDTO(Pelicula pelicula) {
        GetPeliculaDTO getPeliculaDTO = new GetPeliculaDTO();

        getPeliculaDTO.setId(pelicula.getId());
        getPeliculaDTO.setTitulo(pelicula.getTitulo());
        getPeliculaDTO.setDirector(pelicula.getDirector());
        getPeliculaDTO.setGenero(pelicula.getGenero());
        getPeliculaDTO.setDuracion(pelicula.getDuracion());
        getPeliculaDTO.setRating(pelicula.getRating());
        getPeliculaDTO.setPrecio(pelicula.getPrecio());

        return getPeliculaDTO;
    }
}
