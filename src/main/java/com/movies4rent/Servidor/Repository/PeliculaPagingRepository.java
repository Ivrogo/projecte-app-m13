package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Pelicula;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PeliculaPagingRepository extends PagingAndSortingRepository<Pelicula, UUID> {
}
