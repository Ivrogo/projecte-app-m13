package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface RankingService {

    ResponseEntity<ResponseDTO> getPeliculasRankingFiltered(int page, int pageSize, String titulo, String director, String genero, Integer ano, String token);

}
