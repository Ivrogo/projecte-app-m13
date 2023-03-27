package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/peliculas")
public class PeliculaControllerImpl implements PeliculaController{

    @Autowired
    private PeliculaService peliculaService;

    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> getPeliculas(String token) {
        return peliculaService.findAll(token);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPelicula(UUID id, String token) {
        return peliculaService.findById(id, token);
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addPelicula(String token, RegisterPeliculaDTO peliculaDTO) {
        return peliculaService.addPelicula(token, peliculaDTO);
    }
}
