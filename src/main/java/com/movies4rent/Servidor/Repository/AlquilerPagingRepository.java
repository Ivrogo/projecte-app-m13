package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Alquiler;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AlquilerPagingRepository extends PagingAndSortingRepository<Alquiler, UUID> {
}
