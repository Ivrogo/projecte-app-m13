package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class CreaAlquilerDTO {

    @Autowired
    private static PeliculaRepository peliculaRepository;
    @Autowired
    private static UsuariRepository usuariRepository;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoAlquiler estado;

    private Integer precio;
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


    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }


    public static Alquiler fromDTOToEntity(CreaAlquilerDTO creaAlquilerDTO) {

        Alquiler alquiler = new Alquiler();
        alquiler.setFechaInicio(creaAlquilerDTO.getFechaInicio());
        alquiler.setFechaFin(creaAlquilerDTO.getFechaFin());
        alquiler.setEstado(creaAlquilerDTO.getEstado());
        alquiler.setPrecio(creaAlquilerDTO.getPrecio());

        return alquiler;
    }
}
