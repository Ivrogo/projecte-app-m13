package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.CreaAlquilerDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Entities.Pelicula;
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
    public ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token, CreaAlquilerDTO crearAlquilerDTO) {

        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)){
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            if (peliculaId == null || usuarioId == null) {
                response.setMessage("Los campos no pueden ser nulos");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            crearAlquilerDTO.setPelicula(peliculaRepository.findById(peliculaId).get().getId());
            crearAlquilerDTO.setUsuari(usuariRepository.findById(usuarioId).get().getId());
            crearAlquilerDTO.setFechaInicio(LocalDate.now());
            crearAlquilerDTO.setFechaFin(crearAlquilerDTO.getFechaInicio().plusMonths(1));
            crearAlquilerDTO.setPrecio(crearAlquilerDTO.getPrecio());
            crearAlquilerDTO.setEstado(EstadoAlquiler.EN_CURSO);

            alquilerRepository.save(CreaAlquilerDTO.fromDTOToEntity(crearAlquilerDTO));
            response.setMessage("Alquiler creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<ResponseDTO> findAlquilerByUser(UUID usuarioId, String token) {

        ResponseDTO response = new ResponseDTO();


        try {
            List<Alquiler> alquieres = alquilerRepository.findByUsuari(usuarioId);
            List<Pelicula> peliculas = new ArrayList<>();

            for (Alquiler alquiler : alquieres) {
                Optional<Pelicula> pelicula = peliculaRepository.findById(alquiler.getPelicula());
                if (pelicula.isPresent()) {
                    peliculas.add(pelicula.get());
                }
            }
        }
    }
}
