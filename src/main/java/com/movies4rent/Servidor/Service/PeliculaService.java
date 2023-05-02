package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface PeliculaService {

    ResponseEntity<ResponseDTO> findPeliculasFiltered(int page, int pageSize,String titulo, String director, String genero, Integer ano, Integer vecesAlquilada, String token, String orden);

    ResponseEntity<ResponseDTO> findById(UUID id, String token);

    ResponseEntity<ResponseDTO> addPelicula(String token, RegisterPeliculaDTO peliculaDTO);

    ResponseEntity<ResponseDTO> updatePelicula(PeliculaUpdateDTO peliculaUpdateDTO, UUID id, String token);

    ResponseEntity<ResponseDTO> deletePelicula(UUID id, String token);
}
