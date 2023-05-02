package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface RankingController {

   ResponseEntity<ResponseDTO> getPeliculasRanking(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) String titulo, @RequestParam(required = false) String director, @RequestParam(required = false) String genero,
                                                   @RequestParam(required = false) Integer ano, @RequestParam(value = "token", required = true) String token);

}
