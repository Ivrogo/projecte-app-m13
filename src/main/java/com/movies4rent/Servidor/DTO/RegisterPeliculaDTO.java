package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

public class RegisterPeliculaDTO {

    private String titulo;

    private String director;

    private String genero;

    private Double duracion;

    private Double precio;


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

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public static Pelicula fromDTOToEntity(RegisterPeliculaDTO peliculaDTO){

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(peliculaDTO.getTitulo());
        pelicula.setDirector(peliculaDTO.getDirector());
        pelicula.setGenero(peliculaDTO.getGenero());
        pelicula.setDuracion(peliculaDTO.getDuracion());
        pelicula.setPrecio(peliculaDTO.getPrecio());

        return pelicula;
    }
}
