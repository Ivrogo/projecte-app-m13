package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlquilerRepository extends JpaRepository<Alquiler, UUID> {

    Optional<Alquiler> findById(UUID id);
}
