package com.movies4rent.Servidor.Controller;


import com.movies4rent.Servidor.DTO.CreaAlquilerDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/peliculas/alquiler")
public class AlquilerControllerImpl implements AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @Override
    @PostMapping("/nuevo")
    public ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token, CreaAlquilerDTO creaAlquilerDTO) {
        return alquilerService.crearAlquiler(peliculaId, usuarioId, token, creaAlquilerDTO);
    }
}
