package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/peliculas/ranking")
public class RankingControllerImpl implements RankingController {
    @Autowired
    private RankingService rankingService;


    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> getPeliculasRanking(int page, int pageSize, String titulo, String director, String genero, Integer ano, String token) {
        return rankingService.getPeliculasRankingFiltered(page, pageSize, titulo, director, genero, ano, token);
    }
}
