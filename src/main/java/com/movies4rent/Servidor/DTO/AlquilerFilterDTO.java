package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Alquiler;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Predicate;

public class AlquilerFilterDTO {

    private UUID pelicula;

    private UUID usuari;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Integer precio;


    public AlquilerFilterDTO(UUID pelicula, UUID usuari, LocalDate fechaInicio, LocalDate fechaFin, Integer precio) {
        this.pelicula = pelicula;
        this.usuari = usuari;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precio = precio;
    }

    public Predicate<Alquiler> getPredicate() {
        Predicate<Alquiler> predicate = alquiler-> true;
        if (pelicula != null) {
            predicate = predicate.and(alquiler -> alquiler.getPelicula().equals(pelicula));
        }
        if (usuari != null) {
            predicate = predicate.and(alquiler -> alquiler.getUsuari().equals(usuari));
        }
        if (fechaInicio != null) {
            predicate = predicate.and(alquiler -> alquiler.getFechaInicio() == fechaInicio);
        }
        if (fechaFin != null) {
            predicate = predicate.and(alquiler -> alquiler.getFechaFin() == fechaFin);
        }
        if (precio != null )  {
            predicate = predicate.and(alquiler -> alquiler.getPrecio() == precio);
        }
        return predicate;
    }
}
