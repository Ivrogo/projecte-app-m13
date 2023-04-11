package com.movies4rent.Servidor.Controller;


import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.AlquilerService;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/peliculas/alquileres")
public class AlquilerControllerImpl implements AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @Override
    @PostMapping("/nuevo")
    public ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token) {
        return alquilerService.crearAlquiler(peliculaId, usuarioId, token);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ResponseDTO> findAlquilerPaged(int page, int pageSize, String token) {
        return alquilerService.findAlquilerPaged(page, pageSize, token);
    }

    @Override
    @GetMapping("/filterby")
    public ResponseEntity<ResponseDTO> findAlquilerByFilter(int page, int pageSize, UUID peliculaId, UUID usuariId, LocalDate fechaInicio, LocalDate fechaFin, Integer precio,String orden,  String token) {
        return alquilerService.findAlquilerFiltered(page, pageSize, peliculaId, usuariId, fechaInicio, fechaFin, precio, orden, token);
    }

    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> findAlquiler(String token) {
        return alquilerService.findAlquiler(token);
    }

    @Override
    @GetMapping("/alquilerByUser/{usuarioId}")
    public ResponseEntity<ResponseDTO> findAlquilerByUsuari(UUID usuarioId, String token) {
        return alquilerService.findAlquilerByUser(usuarioId, token);
    }

    @Override
    @GetMapping("/{alquilerId}")
    public ResponseEntity<ResponseDTO> findAlquilerById(UUID alquilerId, String token) {
        return alquilerService.findAlquilerById(alquilerId, token);
    }

    @Override
    @PutMapping("/updateStatus")
    public ResponseEntity<ResponseDTO> updateAlquilerEstado(EstadoAlquiler estadoAlquiler, UUID alquilerId, String token) {
        return alquilerService.updateAlquilerEstado(estadoAlquiler, alquilerId, token);
    }

    @Override
    @DeleteMapping("/delete/{alquilerId}")
    public ResponseEntity<ResponseDTO> deleteAlquiler(UUID alquilerId, String token) {
        return alquilerService.deleteAlquiler(alquilerId, token);
    }
}
