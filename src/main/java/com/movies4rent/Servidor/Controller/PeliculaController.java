package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface PeliculaController {

    ResponseEntity<ResponseDTO> getPeliculas(@RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> getPeliculasPaged(@RequestParam int page, @RequestParam int pageSize, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> getPelicula(@PathVariable UUID id, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO>  addPelicula(@RequestParam(value = "token", required = true) String token, @RequestBody RegisterPeliculaDTO peliculaDTO);

    ResponseEntity<ResponseDTO> updatePelicula(@RequestBody PeliculaUpdateDTO peliculaUpdateDTO, @PathVariable UUID id, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> deletePelicula(@PathVariable UUID id, @RequestParam(value = "token", required = true) String token);
}
