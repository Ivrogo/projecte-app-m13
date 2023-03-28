package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreaAlquilerDTO {

    @Autowired
    private static PeliculaRepository peliculaRepository;
    @Autowired
    private static UsuariRepository usuariRepository;

    private UUID id;
    private UUID pelicula;
    private UUID usuari;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoAlquiler estado;

    private BigDecimal precio;
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    public UUID getPelicula() {
        return pelicula;
    }

    public void setPelicula(UUID pelicula) {
        this.pelicula = pelicula;
    }

    public UUID getUsuari() {
        return usuari;
    }

    public void setUsuari(UUID usuari) {
        this.usuari = usuari;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static Alquiler fromDTOToEntity(CreaAlquilerDTO creaAlquilerDTO) {

        Alquiler alquiler = new Alquiler();
        alquiler.setId(creaAlquilerDTO.getId());
        alquiler.setPelicula(creaAlquilerDTO.getPelicula());
        alquiler.setUsuari(creaAlquilerDTO.getUsuari());
        alquiler.setFechaInicio(creaAlquilerDTO.getFechaInicio());
        alquiler.setFechaFin(creaAlquilerDTO.getFechaFin());
        alquiler.setEstado(creaAlquilerDTO.getEstado());
        alquiler.setPrecio(creaAlquilerDTO.getPrecio());

        return alquiler;
    }
}
