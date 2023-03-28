package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.CreaAlquilerDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface AlquilerController {

    ResponseEntity<ResponseDTO> crearAlquiler(@PathVariable UUID peliculaId, @PathVariable UUID usuarioId, @RequestParam(value = "token") String token, @RequestBody CreaAlquilerDTO creaAlquilerDTO);

    ResponseEntity<ResponseDTO> findAlquilerByUsuari (@PathVariable UUID usuarioId, @RequestParam(value = "token") String token);
}
