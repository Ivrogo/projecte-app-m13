package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

public interface AlquilerController {

    ResponseEntity<ResponseDTO> crearAlquiler(@RequestParam(value = "peliculaId") UUID peliculaId, @RequestParam(value = "usuariId") UUID usuariId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> findAlquilerPaged(@RequestParam int page, @RequestParam int pageSize, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> findAlquilerByFilter(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) UUID peliculaId, @RequestParam(required = false) UUID usuariId,
                                                     @RequestParam(required = false)LocalDate fechaInicio, @RequestParam(required = false) LocalDate fechaFin,
                                                     @RequestParam(required = false) Integer precio, @RequestParam(required = false) String orden, @RequestParam(value = "token", required = true)String token);

    ResponseEntity<ResponseDTO> findAlquiler(@RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> findAlquilerByUsuari (@PathVariable UUID usuarioId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> findAlquilerById (@PathVariable UUID alquilerId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> updateAlquilerEstado(@RequestParam EstadoAlquiler estadoAlquiler, @RequestParam UUID alquilerId, @RequestParam(value = "token") String token);

    ResponseEntity<ResponseDTO> deleteAlquiler(@PathVariable UUID alquilerId, @RequestParam(value = "token") String token);
}
