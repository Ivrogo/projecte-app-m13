package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Pelicula;

import java.util.function.Predicate;

/**
 * Clase DTO con los filtros para filtrar las películas.
 * @author Ivan Rodriguez Gomez
 */
public class PeliculaFilterDTO {
    private String titulo;
    private String director;
    private String genero;
    private Integer año;
    private Integer vecesAlquilada;

    public PeliculaFilterDTO(String titulo, String director, String genero, Integer año, Integer vecesAlquilada) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.año = año;
        this.vecesAlquilada = vecesAlquilada;
    }

    public Predicate<Pelicula> getPredicate() {
        Predicate<Pelicula> predicate = pelicula -> true;
        if (titulo != null && !titulo.isEmpty()) {
            predicate = predicate.and(pelicula -> pelicula.getTitulo().equalsIgnoreCase(titulo));
        }
        if (director != null && !director.isEmpty()) {
            predicate = predicate.and(pelicula -> pelicula.getDirector().equalsIgnoreCase(director));
        }
        if (genero != null && !genero.isEmpty()) {
            predicate = predicate.and(pelicula -> pelicula.getGenero().equalsIgnoreCase(genero));
        }
        if (año != null && año >= 0) {
            predicate = predicate.and(pelicula -> pelicula.getAño() == año);
        }
        if (vecesAlquilada != null && vecesAlquilada >= 0) {
            predicate = predicate.and(pelicula -> pelicula.getVecesAlquilada() == vecesAlquilada);
        }
        return predicate;
    }
}
