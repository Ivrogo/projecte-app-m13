package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updatePelicula(PeliculaUpdateDTO peliculaUpdateDTO, UUID id, String token) {
        return peliculaService.updatePelicula(peliculaUpdateDTO, id, token);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deletePelicula(UUID id, String token) {
        return peliculaService.deletePelicula(id, token);
    }

}