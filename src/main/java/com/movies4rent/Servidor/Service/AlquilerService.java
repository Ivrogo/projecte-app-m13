package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.CreaAlquilerDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AlquilerService {

    ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token, CreaAlquilerDTO crearAlquilerDTO);

    ResponseEntity<ResponseDTO> findAlquilerByUser(UUID usuarioId, String token);
}
