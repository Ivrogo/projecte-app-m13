package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Clase entidad del repositorio Alquiler.
 * @author Ivan Rodriguez Gomez.
 */
public interface AlquilerRepository extends JpaRepository<Alquiler, UUID> {

    List<Alquiler> findByPelicula (UUID pelicula);

    List<Alquiler> findByUsuari (UUID usuario);
}
