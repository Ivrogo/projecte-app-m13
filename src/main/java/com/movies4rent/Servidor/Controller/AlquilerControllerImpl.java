package com.movies4rent.Servidor.Controller;


import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Service.AlquilerService;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clase implementation de la interficie AlquilerController.
 * @author Ivan Rodriguez Gomez.
 */
@RestController
@RequestMapping("/peliculas/alquileres")
public class AlquilerControllerImpl implements AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    /**
     * Metodo que respon a la petició de crear un nou lloguer.
     * @method POST
     * @param peliculaId
     * @param usuarioId
     * @param token
     * @return el responseEntity del servei crearAlquiler.
     */
    @Override
    @PostMapping("/nuevo")
    public ResponseEntity<ResponseDTO> crearAlquiler(UUID peliculaId, UUID usuarioId, String token) {
        return alquilerService.crearAlquiler(peliculaId, usuarioId, token);
    }

    /**
     * Metode que respon a la petició de llistar els lloguers del servidor.
     * @method GET
     * @param page
     * @param pageSize
     * @param peliculaId
     * @param usuariId
     * @param fechaInicio
     * @param fechaFin
     * @param precio
     * @param orden
     * @param token
     * @return el responseEntity del servei findAlquilerFiltered.
     */
    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> findAlquilerFiltered(int page, int pageSize, UUID peliculaId, UUID usuariId, LocalDate fechaInicio, LocalDate fechaFin, Integer precio,String orden,  String token) {
        return alquilerService.findAlquilerFiltered(page, pageSize, peliculaId, usuariId, fechaInicio, fechaFin, precio, orden, token);
    }

    /**
     * Metode que respon a la petició de llistar els lloguers d'un usuari en especific.
     * @method GET
     * @param usuarioId
     * @param token
     * @return el responseEntity del servei findAlquilerByUser.
     */
    @Override
    @GetMapping("/alquilerByUser/{usuarioId}")
    public ResponseEntity<ResponseDTO> findAlquilerByUsuari(UUID usuarioId, String token) {
        return alquilerService.findAlquilerByUser(usuarioId, token);
    }

    /**
     * Metode que respon a la petició de mostrar un lloguer en especific.
     * @method GET
     * @param alquilerId
     * @param token
     * @return el responseEntity del servei findAlquilerById.
     */
    @Override
    @GetMapping("/{alquilerId}")
    public ResponseEntity<ResponseDTO> findAlquilerById(UUID alquilerId, String token) {
        return alquilerService.findAlquilerById(alquilerId, token);
    }

    /**
     * Metode que respon a la petició de actualizar l'estat d'un lloguer.
     * @method PUT
     * @param estadoAlquiler
     * @param alquilerId
     * @param token
     * @return el responseEntity del servei updateAlquilerEstado.
     */
    @Override
    @PutMapping("/updateStatus")
    public ResponseEntity<ResponseDTO> updateAlquilerEstado(EstadoAlquiler estadoAlquiler, UUID alquilerId, String token) {
        return alquilerService.updateAlquilerEstado(estadoAlquiler, alquilerId, token);
    }

    /**
     * Metode que respon a la petició de eliminar un lloguer.
     * @method DELETE
     * @param alquilerId
     * @param token
     * @return el responseEntity del servei deleteAlquiler.
     */
    @Override
    @DeleteMapping("/delete/{alquilerId}")
    public ResponseEntity<ResponseDTO> deleteAlquiler(UUID alquilerId, String token) {
        return alquilerService.deleteAlquiler(alquilerId, token);
    }
}
