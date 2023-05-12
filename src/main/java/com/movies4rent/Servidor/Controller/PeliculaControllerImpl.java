package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Clase implementation de la interficie peliculaController.
 * @author Ivan Rodriguez Gomez.
 */
@RestController
@RequestMapping("/peliculas")
public class PeliculaControllerImpl implements PeliculaController{

    @Autowired
    private PeliculaService peliculaService;

    /**
     * Metodo que respon a la peticio de listar las peliculas.
     * @method GET.
     * @param page
     * @param pageSize
     * @param director
     * @param genero
     * @param año
     * @param vecesAlquilada
     * @param token
     * @param orden
     * @return el responseEntity del servei peliculaService findPeliculasFiltered.
     */
    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> getPeliculasFiltered(int page, int pageSize, String director, String genero, Integer ano, Integer vecesAlquilada, String token, String orden) {
        return peliculaService.findPeliculasFiltered(page, pageSize, director, genero, ano, vecesAlquilada, token, orden);
    }

    /**
     * Metodo que respon a la peticio de obtenir una pelicula.
     * @method GET
     * @param id
     * @param token
     * @return el responseEntity del servei peliculaService findById.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPelicula(UUID id, String token) {
        return peliculaService.findById(id, token);
    }

    /**
     * Metode que respon a la peticio de agregar una pelicula.
     * @method POST
     * @param token
     * @param peliculaDTO
     * @return el responseEntity del servei peliculaService addPelicula.
     */
    @Override
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addPelicula(String token, RegisterPeliculaDTO peliculaDTO) {
        return peliculaService.addPelicula(token, peliculaDTO);
    }

    /**
     * Metodo que respon a la peticio de actualizar una pelicula.
     * @method PUT
     * @param peliculaUpdateDTO
     * @param id
     * @param token
     * @return el responseEntity del servei peliculaService updatePelicula.
     */
    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updatePelicula(PeliculaUpdateDTO peliculaUpdateDTO, UUID id, String token) {
        return peliculaService.updatePelicula(peliculaUpdateDTO, id, token);
    }

    /**
     * Metode que respon a la peticio de eliminar una pelicula.
     * @method DELETE
     * @param id
     * @param token
     * @return el responseEntity del servei peliculaService deletePelicula.
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deletePelicula(UUID id, String token) {
        return peliculaService.deletePelicula(id, token);
    }

}
