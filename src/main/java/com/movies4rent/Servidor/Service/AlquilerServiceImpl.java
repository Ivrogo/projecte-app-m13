package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.AlquilerFilterDTO;
import com.movies4rent.Servidor.DTO.GetAlquilerDTO;
import com.movies4rent.Servidor.DTO.GetPeliculaDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.AlquilerPagingRepository;
import com.movies4rent.Servidor.Repository.AlquilerRepository;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que executa els metodes cridats dins de la classe controller.
 *
 * @author Ivan Rodriguez Gomez
 */
@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private AlquilerPagingRepository alquilerPagingRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private EmailService emailService;

    /**
     * Metode que crea un lloguer dins de la base de dades.
     *
     * @param peliculaId
     * @param usuariId
     * @param token
     * @return un responseEntity amb un body amb les dades del lloguer i confirmant la insercció de les dades del lloguer dins la BDD.
     */
    @Override
    public ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuariId, String token) {

        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
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
            if (!foundPelicula.isPresent() || !foundUsuari.isPresent()) {
                response.setMessage("La pelicula o el usuario no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Alquiler alquiler = new Alquiler();
            alquiler.setFechaInicio(LocalDate.now());
            alquiler.setFechaFin(LocalDate.now().plusMonths(1));
            alquiler.setEstado(EstadoAlquiler.EN_CURSO);
            alquiler.setPrecio(foundPelicula.get().getPrecio());
            alquiler.setPelicula(foundPelicula.get().getId());
            foundPelicula.get().setVecesAlquilada(foundPelicula.get().getVecesAlquilada() + 1);
            alquiler.setUsuari(foundUsuari.get().getId());

            peliculaRepository.save(foundPelicula.get());
            alquilerRepository.save(alquiler);

            emailService.sendEmail(alquiler, foundUsuari.get().getEmail());

            response.setMessage("Alquiler creado correctamente");
            response.setValue(alquiler);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Metode que llista tots els usuaris registrats en la base de dades paginats i amb la possibilitat de filtrarlos.
     *
     * @param page        numero de la pagina
     * @param pageSize    quantitat de elements per pagina
     * @param peliculaId  variable id que filtra la llista segons el la id de la pelicula
     * @param usuariId    variable id que filtra la llista segons els la id de l'usuari
     * @param fechaInicio variable date que filtra la llista segons la data d'inici.
     * @param fechaFin    variable date que filtra la llista segons la data d'final.
     * @param precio      variable integer que filtra la llista segons el preu.
     * @param orden       variable que serveix per organitzar la llista.
     * @param token       token de sessió
     * @return un DTO amb el value de l'objecte d'una llista de lloguers
     */
    @Override
    public ResponseEntity<ResponseDTO> findAlquilerFiltered(int page, int pageSize, UUID peliculaId, UUID usuariId, LocalDate fechaInicio, LocalDate fechaFin, Integer precio, String orden, String token) {

        ResponseDTO<Page<Alquiler>> response = new ResponseDTO();
        PageRequest pr = PageRequest.of(page, pageSize);

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            AlquilerFilterDTO filter = new AlquilerFilterDTO(peliculaId, usuariId, fechaInicio, fechaFin, precio);

            Page<Alquiler> foundAlquileres;
            //Comprobamos si existe un orden o no
            if (orden == null || orden.isEmpty()) {
                //Comprobamos si existe algun filtro o no
                if (peliculaId == null && usuariId == null && fechaInicio == null && fechaFin == null && precio == null) {
                    response.setMessage("Mostrando alquileres...");
                    foundAlquileres = alquilerRepository.findAll(pr);
                    if (foundAlquileres.isEmpty()) {
                        response.setMessage("No existen alquileres");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    }
                    response.setValue(foundAlquileres);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    List<Alquiler> alquileresFiltradosSinOrdenar = alquilerRepository.findAll().stream().filter(filter.getPredicate()).collect(Collectors.toList());
                    foundAlquileres = PageableExecutionUtils.getPage(alquileresFiltradosSinOrdenar, pr, alquileresFiltradosSinOrdenar::size);
                    response.setMessage("Mostrando los alquileres filtrados y sin ordenar...");
                    response.setValue(foundAlquileres);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

            } else {
                Comparator<Alquiler> comparator = null;
                switch (orden) {
                    case "peliculaIdAsc":
                        comparator = Comparator.comparing(Alquiler::getPelicula);
                        break;
                    case "peliculaIdDesc":
                        comparator = Comparator.comparing(Alquiler::getPelicula).reversed();
                        break;
                    case "usuariIdAsc":
                        comparator = Comparator.comparing(Alquiler::getUsuari);
                        break;
                    case "usuariIdDesc":
                        comparator = Comparator.comparing(Alquiler::getUsuari).reversed();
                        break;
                    case "fechaInicioAsc":
                        comparator = Comparator.comparing(Alquiler::getFechaInicio);
                        break;
                    case "fechaInicioDesc":
                        comparator = Comparator.comparing(Alquiler::getFechaInicio).reversed();
                        break;
                    case "fechaFinAsc":
                        comparator = Comparator.comparing(Alquiler::getFechaFin);
                        break;
                    case "fechaFinDesc":
                        comparator = Comparator.comparing(Alquiler::getFechaFin).reversed();
                        break;
                    case "precioAsc":
                        comparator = Comparator.comparing(Alquiler::getPrecio);
                        break;
                    case "precioDesc":
                        comparator = Comparator.comparing(Alquiler::getPrecio).reversed();
                        break;
                    default:
                        break;
                }
                if (peliculaId == null && usuariId == null && fechaInicio == null && fechaFin == null && precio == null) {
                    Page<Alquiler> foundAlquileresOrdenadosSinFiltrar = alquilerRepository.findAll(pr);
                    foundAlquileres = new PageImpl<>(comparator != null ? foundAlquileresOrdenadosSinFiltrar.getContent().stream().sorted(comparator).collect(Collectors.toList()) : foundAlquileresOrdenadosSinFiltrar.getContent(), pr, foundAlquileresOrdenadosSinFiltrar.getTotalElements());
                    response.setMessage("Mostrando alquileres ordenados y sin filtrar...");
                    response.setValue(foundAlquileres);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                List<Alquiler> foundAlquileresFiltered = alquilerRepository.findAll().stream().filter(filter.getPredicate()).sorted(comparator).collect(Collectors.toList());
                foundAlquileres = PageableExecutionUtils.getPage(foundAlquileresFiltered, pr, foundAlquileresFiltered::size);
                response.setMessage("mostrando alquileres filtrados y ordenados...");
                response.setValue(foundAlquileres);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metode que llista els lloguers d'un usuari especific dins de la base de dades.
     *
     * @param usuarioId
     * @param token
     * @return un responseEntity amb tots els lloguers realitzats d'un usuari especific dins de la base de dades.
     */
    @Override
    public ResponseEntity<ResponseDTO> findAlquilerByUser(UUID usuarioId, String token) {

        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
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
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metode que troba un lloguer en especific segons la seva id.
     *
     * @param alquilerId
     * @param token
     * @return un responseEntity amb el lloguer en especific.
     */
    @Override
    public ResponseEntity<ResponseDTO> findAlquilerById(UUID alquilerId, String token) {

        ResponseDTO<GetAlquilerDTO> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Alquiler> foundAlquiler = alquilerRepository.findById(alquilerId);
            if (!foundAlquiler.isPresent()) {
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

    /**
     * Metode que actualiza el estado del lloguer dins de la base de dades.
     *
     * @param estado
     * @param alquilerId
     * @param token
     * @return un responseEntity amb la confirmació del canvi de estado del lloguer dins de la base de dades.
     */
    @Override
    public ResponseEntity<ResponseDTO> updateAlquilerEstado(EstadoAlquiler estado, UUID alquilerId, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Alquiler> updatedAlquiler = alquilerRepository.findById(alquilerId);
            Optional<Usuari> foundUsuari = usuariRepository.findById(updatedAlquiler.get().getUsuari());
            if (!updatedAlquiler.isPresent()) {
                response.setMessage("Alquiler no trobat");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            if (updatedAlquiler.get().getFechaFin() == LocalDate.now()) {
                updatedAlquiler.get().setEstado(EstadoAlquiler.FINALIZADO);
            }

            updatedAlquiler.get().setEstado(estado);
            alquilerRepository.save(updatedAlquiler.get());

            if (updatedAlquiler.get().getEstado() == EstadoAlquiler.FINALIZADO) {
                emailService.sendEmailFinalizacion(updatedAlquiler.get(), foundUsuari.get().getEmail());
            }

            response.setValue(updatedAlquiler.get());
            response.setMessage("Estado del alquiler actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metode que elimina un lloguer dins de la base de dades.
     *
     * @param alquilerId
     * @param token
     * @return un responseEntity amb la confirmació de la eliminació del lloguer dins de la base de dades.
     */
    @Override
    public ResponseEntity<ResponseDTO> deleteAlquiler(UUID alquilerId, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
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
