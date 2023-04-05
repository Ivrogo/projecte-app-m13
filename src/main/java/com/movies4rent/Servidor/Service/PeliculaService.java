package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface PeliculaService {

    ResponseEntity<ResponseDTO> findAll(String token);

    ResponseEntity<ResponseDTO> findAllPaged(int page, int pageSize, String token, String orden);

    ResponseEntity<ResponseDTO> findPeliculasFiltred(int page, int pageSize, String director, String genero, Integer año, Integer vecesAlquilada, String token, String orden);

    ResponseEntity<ResponseDTO> findById(UUID id, String token);

    ResponseEntity<ResponseDTO> addPelicula(String token, RegisterPeliculaDTO peliculaDTO);

    ResponseEntity<ResponseDTO> updatePelicula(PeliculaUpdateDTO peliculaUpdateDTO, UUID id, String token);

    ResponseEntity<ResponseDTO> deletePelicula(UUID id, String token);
}
