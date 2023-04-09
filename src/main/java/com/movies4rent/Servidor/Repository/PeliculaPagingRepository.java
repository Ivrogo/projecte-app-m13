package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Repositorio de Peliculas paginadas.
 */
public interface PeliculaPagingRepository extends PagingAndSortingRepository<Pelicula, UUID> {

    @Query("SELECT p FROM Pelicula p WHERE p.director = :director")
    Page<Pelicula> findByDirector(int page, int pageSize, @Param("director") String director, @RequestParam(value = "token", required = true) String token);

    @Query("SELECT p FROM Pelicula p WHERE p.genero = :genero")
    Page<Pelicula> findByGenero(int page, int pageSize, @Param("genero") String genero, @RequestParam(value = "token", required = true) String token);

    @Query("SELECT p FROM Pelicula p WHERE p.año = :año")
    Page<Pelicula> findByAno(int page, int pageSize, @Param("año") int año, @RequestParam(value = "token", required = true) String token);


}
