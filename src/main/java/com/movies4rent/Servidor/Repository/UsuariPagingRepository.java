package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UsuariPagingRepository extends PagingAndSortingRepository<Usuari, UUID> {

}
