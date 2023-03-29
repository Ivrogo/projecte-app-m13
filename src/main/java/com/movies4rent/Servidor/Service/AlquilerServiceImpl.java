package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.CreaAlquilerDTO;
import com.movies4rent.Servidor.DTO.GetAlquilerDTO;
import com.movies4rent.Servidor.DTO.GetPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.AlquilerRepository;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuariId, String token, CreaAlquilerDTO crearAlquilerDTO) {

        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)){
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            if (peliculaId == null || usuariId == null) {
                response.setMessage("Los campos no pueden ser nulos");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            Optional<Pelicula> foundPelicula = peliculaRepository.findById(peliculaId);
            Optional<Usuari> foundUsuari = usuariRepository.findById(usuariId);
            if (!foundPelicula.isPresent() ||!foundUsuari.isPresent()) {
                response.setMessage("La pelicula o el usuario no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            crearAlquilerDTO.setFechaInicio(LocalDate.now());
            crearAlquilerDTO.setFechaFin(LocalDate.now().plusMonths(1));
            crearAlquilerDTO.setPrecio(crearAlquilerDTO.getPrecio());
            crearAlquilerDTO.setEstado(EstadoAlquiler.EN_CURSO);

            Alquiler alquiler = CreaAlquilerDTO.fromDTOToEntity(crearAlquilerDTO);
            alquiler.setPelicula(foundPelicula.get().getId());
            alquiler.setUsuari(foundUsuari.get().getId());

            alquilerRepository.save(alquiler);
            response.setMessage("Alquiler creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<ResponseDTO> findAlquiler(String token) {

        ResponseDTO<List<GetAlquilerDTO>> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Alquiler> alquileres = alquilerRepository.findAll();
            List<GetAlquilerDTO> alquileresDTO = new ArrayList<>();
            alquileres.forEach(x -> alquileresDTO.add(GetAlquilerDTO.fromEntityToDTO(x)));
            if (alquileres.size() <= 0) {
                response.setMessage("No hay alquileres");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setMessage("Mostrando todos los alquileres registrados");
            response.setValue(alquileresDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ResponseDTO> findAlquilerByUser(UUID usuarioId, String token) {

        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)){
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Alquiler> alquileres = alquilerRepository.findByUsuari(usuarioId);
            List<Pelicula> peliculas = new ArrayList<>();

            for (Alquiler alquiler : alquileres) {
                Optional<Pelicula> pelicula = peliculaRepository.findById(alquiler.getPelicula());
                if (!pelicula.isPresent()) {
                    response.setMessage("No se encontro ningun pelicula");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                peliculas.add(pelicula.get());
            }
            List<GetPeliculaDTO> peliculaDTO = peliculas.stream().map(GetPeliculaDTO::fromEntityToDTO).toList();
            response.setValue(peliculaDTO);
            response.setMessage("Peliculas encontradas");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> findAlquilerById(UUID alquilerId, String token) {

        ResponseDTO<GetAlquilerDTO> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)){
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Alquiler> foundAlquiler = alquilerRepository.findById(alquilerId);
            if (!foundAlquiler.isPresent()){
                response.setMessage("Alquiler no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setMessage("Mostrando la información del alquiler");
            response.setValue(GetAlquilerDTO.fromEntityToDTO(foundAlquiler.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updateAlquilerEstado(EstadoAlquiler estado, UUID alquilerId, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Alquiler> updatedAlquiler = alquilerRepository.findById(alquilerId);
            if (!updatedAlquiler.isPresent()) {
                response.setMessage("Alquiler no trobat");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            updatedAlquiler.get().setEstado(estado);
            alquilerRepository.save(updatedAlquiler.get());

            response.setValue(updatedAlquiler.get());
            response.setMessage("Estado del alquiler actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteAlquiler(UUID alquilerId, String token) {
        ResponseDTO response = new ResponseDTO();

        if(!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Alquiler> toDeleteAlquiler = alquilerRepository.findById(alquilerId);
            if (!toDeleteAlquiler.isPresent()) {
                response.setMessage("Alquiler no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            alquilerRepository.deleteById(alquilerId);
            response.setMessage("Alquiler eliminado");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
