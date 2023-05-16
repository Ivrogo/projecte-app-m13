package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase implementation de la interficie RankingController.
 * @author Ivan Rodriguez Gomez.
 */
@RestController
@RequestMapping("/peliculas/ranking")
public class RankingControllerImpl implements RankingController {
    @Autowired
    private RankingService rankingService;

    /**
     * Metode que respon a la petició de mostrar el ranking de pelicules amb els seus filtres.
     * @param page parametre que permet establir quina pagina es vol veure
     * @param pageSize parametre que estableix la quantitat d'elements dins de cada pagina
     * @param titulo parametre de filtre
     * @param director parametre de filtre
     * @param genero parametre de filtre
     * @param ano parametre de filtre
     * @param token token de sesió
     * @method GET.
     * @author Iván Rodriguez Gomez.
     */
    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> getPeliculasRanking(int page, int pageSize, String titulo, String director, String genero, Integer ano, String token) {
        return rankingService.getPeliculasRankingFiltered(page, pageSize, titulo, director, genero, ano, token);
    }
}
