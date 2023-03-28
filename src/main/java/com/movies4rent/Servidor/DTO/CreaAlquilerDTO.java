package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreaAlquilerDTO {

    @Autowired
    private static PeliculaRepository peliculaRepository;
    @Autowired
    private static UsuariRepository usuariRepository;
    private Pelicula pelicula;
    private Usuari usuari;
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

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public static Alquiler fromDTOToEntity(CreaAlquilerDTO creaAlquilerDTO) {

        Alquiler alquiler = new Alquiler();
        alquiler.setPelicula(peliculaRepository.findById(creaAlquilerDTO.pelicula.getId()).get());
        alquiler.setUsuari(usuariRepository.findById(creaAlquilerDTO.getUsuari().getId()).get());
        alquiler.setFechaInicio(creaAlquilerDTO.getFechaInicio());
        alquiler.setFechaFin(creaAlquilerDTO.getFechaFin());
        alquiler.setEstado(creaAlquilerDTO.getEstado());
        alquiler.setPrecio(peliculaRepository.findById(creaAlquilerDTO.pelicula.getId()).get().getPrecio());

        return alquiler;
    }
}
