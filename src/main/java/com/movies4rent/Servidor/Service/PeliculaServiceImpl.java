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
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe que executa els metodes cridats dins de la classe controller.
 * @author Ivan Rodriguez Gomez
 */
@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private PeliculaPagingRepository peliculaPagingRepository;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Metode que llista totes les pelicules amb filtres i sorting.
     * @param page
     * @param pageSize
     * @param director
     * @param genero
     * @param año
     * @param vecesAlquilada
     * @param token
     * @param orden
     * @return un responseEntity amb una llista de peliculas.
     */
    @Override
    public ResponseEntity<ResponseDTO> findPeliculasFiltered(int page, int pageSize, String director, String genero, Integer año, Integer vecesAlquilada, String token, String orden) {

        ResponseDTO<Page<Pelicula>> response = new ResponseDTO();
        PageRequest pr = PageRequest.of(page, pageSize);

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            PeliculaFilterDTO filter = new PeliculaFilterDTO(director, genero, año, vecesAlquilada);

            Page<Pelicula> foundPeliculas;
            if (orden == null || orden.isEmpty()) {
                if (director == null && genero == null && año == null && vecesAlquilada == null) {
                    response.setMessage("Mostrando peliculas...");
                    foundPeliculas = peliculaRepository.findAll(pr);

                    if (foundPeliculas.isEmpty()) {
                        response.setMessage("No hay peliculas");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }

                    response.setValue(foundPeliculas);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    List<Pelicula> peliculasFiltradasSinOrdenar = peliculaRepository.findAll().stream().filter(filter.getPredicate()).collect(Collectors.toList());
                    foundPeliculas = PageableExecutionUtils.getPage(peliculasFiltradasSinOrdenar, pr, peliculasFiltradasSinOrdenar::size);
                    response.setMessage("Mostrando peliculas filtradas y sin ordenar");
                    response.setValue(foundPeliculas);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
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
                if (director == null && genero == null && año == null && vecesAlquilada == null) {
                    Page<Pelicula> foundPeliculasOrdenadasSinFiltrar = peliculaRepository.findAll(pr);
                    foundPeliculas = new PageImpl<>(comparator != null ? foundPeliculasOrdenadasSinFiltrar.getContent().stream().sorted(comparator).collect(Collectors.toList()) : foundPeliculasOrdenadasSinFiltrar.getContent(), pr, foundPeliculasOrdenadasSinFiltrar.getTotalElements());
                    response.setMessage("Mostrando peliculas ordenadas sin filtrar");
                    response.setValue(foundPeliculas);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    List<Pelicula> foundPeliculasFiltradasOrdenadas = peliculaRepository.findAll().stream().filter(filter.getPredicate()).sorted(comparator).collect(Collectors.toList());
                    foundPeliculas = PageableExecutionUtils.getPage(foundPeliculasFiltradasOrdenadas, pr, foundPeliculasFiltradasOrdenadas::size);
                    response.setMessage("Mostrando peliculas filtradas y ordenadas");
                    response.setValue(foundPeliculas);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metode que troba una pelicula especifica.
     * @param id
     * @param token
     * @return un responseEntity amb una pelicula.
     */
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

    /**
     * Metode que afegeix una pelicula.
     * @param token
     * @param peliculaDTO
     * @return un responseEntity amb la confirmació de que la pelicula ha estat afegida.
     */
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

    /**
     * Metode que actualiza una pelicula.
     * @param peliculaUpdateDTO
     * @param id
     * @param token
     * @return un responseEntity amb la confirmació de que la pelicula ha estat actualizada i les seves dades actualitzades.
     */
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

    /**
     * Metode que elimina una pelicula.
     * @param id
     * @param token
     * @return un responseEntity amb la confirmació de que la pelicula ha estat eliminada.
     */
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
