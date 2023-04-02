package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;

import java.time.LocalDate;
import java.util.UUID;

public class GetAlquilerDTO {

    private UUID id;

    private UUID peliculaId;

    private UUID usuariId;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private double precio;

    private EstadoAlquiler estado;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(UUID peliculaId) {
        this.peliculaId = peliculaId;
    }

    public UUID getUsuariId() {
        return usuariId;
    }

    public void setUsuariId(UUID usuariId) {
        this.usuariId = usuariId;
    }

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    public static GetAlquilerDTO fromEntityToDTO(Alquiler alquiler) {

        GetAlquilerDTO getAlquilerDTO = new GetAlquilerDTO();

        getAlquilerDTO.setId(alquiler.getId());
        getAlquilerDTO.setPeliculaId(alquiler.getPelicula());
        getAlquilerDTO.setUsuariId(alquiler.getUsuari());
        getAlquilerDTO.setFechaInicio(alquiler.getFechaInicio());
        getAlquilerDTO.setFechaFin(alquiler.getFechaFin());
        getAlquilerDTO.setPrecio(alquiler.getPrecio());
        getAlquilerDTO.setEstado(alquiler.getEstado());

        return getAlquilerDTO;
    }
}
