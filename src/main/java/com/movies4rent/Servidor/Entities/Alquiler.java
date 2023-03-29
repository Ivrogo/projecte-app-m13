package com.movies4rent.Servidor.Entities;

import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lloguers")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private UUID pelicula;

    @Column
    private UUID usuari;

    @Column
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;

    @Column
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column
    private EstadoAlquiler estado;

    public Alquiler(UUID id, UUID pelicula, UUID usuari, LocalDate fechaInicio, LocalDate fechaFin, BigDecimal precio, EstadoAlquiler estado) {
        this.id = id;
        this.pelicula = pelicula;
        this.usuari = usuari;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precio = precio;
        this.estado = estado;
    }

    public Alquiler() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }
}
