package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, UUID> {

    Optional<Pelicula> findById(UUID id);

    Optional<Pelicula> findByTitulo(String titulo);
}
