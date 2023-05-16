package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;
/**
 * Classe interficie per als metodes de gestió de lloguers.
 * @author Iván Rodríguez Gómez
 */
public interface AlquilerService {

    ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token);

    ResponseEntity<ResponseDTO> findAlquilerFiltered(int page, int pageSize, UUID peliculaId, UUID usuariId, LocalDate fechaInicio, LocalDate fechaFin, Integer precio, String orden, String token);

    ResponseEntity<ResponseDTO> findAlquilerByUser(UUID usuarioId, String token);

    ResponseEntity<ResponseDTO> findAlquilerById(UUID alquilerId, String token);

    ResponseEntity<ResponseDTO> updateAlquilerEstado(EstadoAlquiler estado, UUID alquilerId, String Token);

    ResponseEntity<ResponseDTO> deleteAlquiler(UUID alquilerId, String token);
}
