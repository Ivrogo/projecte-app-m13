package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.PeliculaRankingFilterDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Repository.PeliculaPagingRepository;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingServiceImpl implements RankingService{
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaPagingRepository peliculaPagingRepository;

    @Autowired
    private TokenUtils tokenUtils;
    @Override
    public ResponseEntity<ResponseDTO> getPeliculasRankingFiltered(int page, int pageSize, String titulo, String director, String genero, Integer ano, String token) {

        ResponseDTO<Page<Pelicula>> response = new ResponseDTO();
        PageRequest pr = PageRequest.of(page, pageSize);

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            PeliculaRankingFilterDTO filter = new PeliculaRankingFilterDTO(titulo, director, genero, ano);
            Page<Pelicula> RankingPeliculasByVecesAlquilada;

            if (titulo == null && director == null && genero == null && ano == null){
                response.setMessage("Mostrando el ranking de peliculas por las veces que ha sido alquilada");
                RankingPeliculasByVecesAlquilada = peliculaRepository.findByVecesAlquilada(pr);
                if (RankingPeliculasByVecesAlquilada.isEmpty()) {
                    response.setMessage("No hay ranking que mostrar");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }

                response.setValue(RankingPeliculasByVecesAlquilada);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                List<Pelicula> rankingFiltrado = peliculaRepository.findPeliculasByVecesAlquilada().stream().filter(filter.getPredicate()).collect(Collectors.toList());
                int paginaSize = pr.getPageSize();
                int currentPage = pr.getPageNumber();
                int startItem = currentPage * paginaSize;
                List<Pelicula> paginatedRanking;

                if(rankingFiltrado.size() < startItem) {
                    paginatedRanking = Collections.emptyList();
                } else {
                    int toIndex = Math.min(startItem + paginaSize, rankingFiltrado.size());
                    paginatedRanking = rankingFiltrado.subList(startItem, toIndex);
                }
                Page<Pelicula> rankingByVecesAlquiladaFiltradoFinal = new PageImpl<>(paginatedRanking, pr, rankingFiltrado.size());
                response.setMessage("Mostrando el ranking con los filtros indicados");
                response.setValue(rankingByVecesAlquiladaFiltradoFinal);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
