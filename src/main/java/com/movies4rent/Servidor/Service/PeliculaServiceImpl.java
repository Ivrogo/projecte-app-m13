package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.*;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Repository.PeliculaPagingRepository;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaPagingRepository peliculaPagingRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public ResponseEntity<ResponseDTO> findPeliculasFiltered(int page, int pageSize, String director, String genero, Integer año, Integer vecesAlquilada, String token, String orden) {

        ResponseDTO<Page<Pelicula>> response = new ResponseDTO();
        PageRequest pr = PageRequest.of(page, pageSize);

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Pelicula> foundPeliculas = peliculaRepository.findAll();
            Page<Pelicula> peliculasSinFiltroNiOrden = new PageImpl<>(foundPeliculas, pr, foundPeliculas.size());
            PeliculaFilterDTO filter = new PeliculaFilterDTO(director, genero, año, vecesAlquilada);

            if (foundPeliculas.isEmpty()) {
                response.setMessage("No hay peliculas");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (orden == null || orden.isEmpty()) {
                if (director == null && genero == null && año == null && vecesAlquilada == null) {
                    response.setMessage("Mostrando peliculas...");
                    response.setValue(peliculasSinFiltroNiOrden);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                List<Pelicula> peliculasFiltradasSinOrdenar = foundPeliculas.stream().filter(filter.getPredicate()).collect(Collectors.toList());
                Page<Pelicula> peliculasFiltradasUnsorted = new PageImpl<>(peliculasFiltradasSinOrdenar, pr, peliculasFiltradasSinOrdenar.size());

                response.setMessage("Mostrando peliculas filtradas y sin ordenar");
                response.setValue(peliculasFiltradasUnsorted);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {

                Comparator<Pelicula> comparator = null;
                switch (orden) {
                    case "vecesAlquiladaAsc":
                        comparator = Comparator.comparing(Pelicula::getVecesAlquilada);
                        break;
                    case "vecesAlquiladaDesc":
                        comparator = Comparator.comparing(Pelicula::getVecesAlquilada).reversed();
                        break;
                    case "duracionAsc":
                        comparator = Comparator.comparing(Pelicula::getDuracion);
                        break;
                    case "duracionDesc":
                        comparator = Comparator.comparing(Pelicula::getDuracion).reversed();
                        break;
                    case "añoAsc":
                        comparator = Comparator.comparing(Pelicula::getAño);
                        break;
                    case "añoDesc":
                        comparator = Comparator.comparing(Pelicula::getAño).reversed();
                    default:
                        break;
                }
                List<Pelicula> peliculasFinal = foundPeliculas.stream().filter(filter.getPredicate()).sorted(comparator).collect(Collectors.toList());
                Page<Pelicula> peliculas = new PageImpl<>(peliculasFinal, pr, peliculasFinal.size());

                response.setMessage("Mostrando peliculas segun los filtros indicados");
                response.setValue(peliculas);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

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
            }

            Optional<Pelicula> updatedPelicula = peliculaRepository.findById(id);
            if (!updatedPelicula.isPresent()){
                response.setMessage("Pelicula no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }


            PeliculaUpdateDTO.updateEntityFromDTO(updatedPelicula.get(), peliculaUpdateDTO);
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
