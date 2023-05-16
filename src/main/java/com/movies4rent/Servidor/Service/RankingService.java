package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * Classe interficie per als metodes de gestió del ranking de pelicules
 * @author Iván Rodríguez Gómez
 */
public interface RankingService {

    ResponseEntity<ResponseDTO> getPeliculasRankingFiltered(int page, int pageSize, String titulo, String director, String genero, Integer ano, String token);

}
