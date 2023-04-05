package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, UUID> {

    Optional<Pelicula> findById(UUID id);

    Optional<Pelicula> findByTitulo(String titulo);

    @Query("SELECT p FROM Pelicula p WHERE p.director = :director")
    Page<Pelicula> findByDirector(Pageable pageable, @Param("director") String director);

    @Query("SELECT p FROM Pelicula p WHERE p.genero = :genero")
    Page<Pelicula> findByGenero(Pageable pageable, @Param("genero") String genero);

    @Query("SELECT p FROM Pelicula p WHERE p.año = :año")
    Page<Pelicula> findByAño(Pageable pageable, @Param("año") int año);
}
