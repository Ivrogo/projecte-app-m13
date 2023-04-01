package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

public class RegisterPeliculaDTO {

    private String titulo;

    private String director;

    private String genero;

    private int duracion;

    private int año;

    private int precio;


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

    public static Pelicula fromDTOToEntity(RegisterPeliculaDTO peliculaDTO){

        Pelicula pelicula = new Pelicula(
                peliculaDTO.getTitulo(),
                peliculaDTO.getDirector(),
                peliculaDTO.getGenero(),
                peliculaDTO.getDuracion(),
                peliculaDTO.getAño(),
                peliculaDTO.getPrecio()
        );

        return pelicula;
    }
}
