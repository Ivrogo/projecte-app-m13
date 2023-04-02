package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface AlquilerController {

    ResponseEntity<ResponseDTO> crearAlquiler(@RequestParam(value = "peliculaId") UUID peliculaId, @RequestParam(value = "usuariId") UUID usuariId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> findAlquiler(@RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> findAlquilerByUsuari (@RequestParam UUID usuarioId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> findAlquilerById (@RequestParam UUID alquilerId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> updateAlquilerEstado(@RequestParam EstadoAlquiler estadoAlquiler, @RequestParam UUID alquilerId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> deleteAlquiler(@RequestParam UUID alquilerId, @RequestParam(value = "token") String token);
}
