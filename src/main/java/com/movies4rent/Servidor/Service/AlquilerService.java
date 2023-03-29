package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.CreaAlquilerDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AlquilerService {

    ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token, CreaAlquilerDTO crearAlquilerDTO);

    ResponseEntity<ResponseDTO> findAlquiler(String token);

    ResponseEntity<ResponseDTO> findAlquilerByUser(UUID usuarioId, String token);

    ResponseEntity<ResponseDTO> findAlquilerById(UUID alquilerId, String token);

    ResponseEntity<ResponseDTO> updateAlquilerEstado(EstadoAlquiler estado, UUID alquilerId, String Token);

    ResponseEntity<ResponseDTO> deleteAlquiler(UUID alquilerId, String token);
}
