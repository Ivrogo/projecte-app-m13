package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.GetPeliculaDTO;
import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public ResponseEntity<ResponseDTO> findAll(String token) {

        ResponseDTO<List<GetPeliculaDTO>> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)){
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            List<Pelicula> peliculas = peliculaRepository.findAll();
            List<GetPeliculaDTO> peliculaDTOS = new ArrayList<>();
            peliculas.forEach(x -> peliculaDTOS.add(GetPeliculaDTO.fromEntityToDTO(x)));
            if (peliculas.size() <= 0) {
                response.setMessage("No hay peliculas");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setValue(peliculaDTOS);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> findById(UUID id, String token) {

        ResponseDTO<GetPeliculaDTO> response = new ResponseDTO();

        if(!tokenUtils.isTokenValid(token)){
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Pelicula> foundPelicula = peliculaRepository.findById(id);
            if (!foundPelicula.isPresent()) {
                response.setMessage("Pelicula no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setValue(GetPeliculaDTO.fromEntityToDTO(foundPelicula.get()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> addPelicula(String token, RegisterPeliculaDTO peliculaDTO) {

        ResponseDTO response = new ResponseDTO();
        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta acción");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            if (peliculaDTO.getTitulo().isEmpty() || peliculaDTO.getDirector().isEmpty() || peliculaDTO.getGenero().isEmpty()) {
                response.setMessage("Los campos no pueden estar vacios");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else if (peliculaDTO.getDuracion() == null || peliculaDTO.getPrecio() == null) {
                response.setMessage("los campos no pueden ser nulos");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            peliculaRepository.save(RegisterPeliculaDTO.fromDTOToEntity(peliculaDTO));
            response.setMessage("Pelicula añadida correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Pelicula ya existe");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updatePelicula(PeliculaUpdateDTO peliculaUpdateDTO, UUID id, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta acción");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try{
            if (peliculaUpdateDTO.getDirector().isEmpty() || peliculaUpdateDTO.getGenero().isEmpty() || peliculaUpdateDTO.getTitulo().isEmpty()) {
                response.setMessage("Los campos no pueden estar vacios");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else if (peliculaUpdateDTO.getDuracion() == null || peliculaUpdateDTO.getPrecio() == null){
                response.setMessage("Los campos no pueden ser nulos");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            Optional<Pelicula> updatedPelicula = peliculaRepository.findById(id);
            if (!updatedPelicula.isPresent()){
                response.setMessage("Pelicula no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            peliculaRepository.save(updatedPelicula.get());
            response.setMessage("Pelicula actualizada correctamente");
            response.setValue(updatedPelicula.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> deletePelicula(UUID id, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta acción");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Pelicula> foundPelicula = peliculaRepository.findById(id);
            if (!foundPelicula.isPresent()) {
                response.setMessage("Pelicula no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            peliculaRepository.deleteById(id);
            response.setMessage("Pelicula eliminada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}