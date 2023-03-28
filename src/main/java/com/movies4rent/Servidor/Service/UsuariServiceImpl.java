package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.*;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Classe que executa els metodes cridats dins de la classe controller.
 * @author Ivan Rodriguez Gomez
 */
@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Classe que llista tots els usuaris registrats en la base de dades.
     * @param token token de sessió
     * @return un DTO amb el value de l'objecte d'una llista d'usuaris
     */
    @Override
    public ResponseEntity<ResponseDTO> findAll(String token) {
        ResponseDTO<List<GetUsuariDTO>> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta accion");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Usuari> usuaris = usuariRepository.findAll();
            List<GetUsuariDTO> usuarisDTO = new ArrayList<>();
            usuaris.forEach(x -> usuarisDTO.add(GetUsuariDTO.fromEntityToDTO(x)));
            if (usuaris.size() <= 0) {
                response.setMessage("No hay usuarios");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setMessage("Mostrando los usuarios registrados");
            response.setValue(usuarisDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que troba un usuari especific en la base de dades.
     * @param id id de l'usuari
     * @param token token de sessió
     * @return DTO amb el value de l'objecte que conté la informació de l'usuari
     */
    @Override
    public ResponseEntity<ResponseDTO> findById(UUID id, String token) {
        ResponseDTO<GetUsuariDTO> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null || !user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Usuari> foundUsuari = usuariRepository.findById(id);
            if (!foundUsuari.isPresent()) {
                response.setMessage("Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setMessage("Mostrando la información del usuario");
            response.setValue(GetUsuariDTO.fromEntityToDTO(foundUsuari.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que actualitza un usuari en la base de dades.
     * @param userUpdateDTO DTO amb la informació canviada de l'usuari
     * @param token token de sessió
     * @return DTO amb un missatge d'actualització
     */
    @Override
    public ResponseEntity<ResponseDTO> updateUser(UserUpdateDTO userUpdateDTO, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null || !user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            UserUpdateDTO.updateEntityFromDTO(user.get(), userUpdateDTO);
            usuariRepository.save(user.get());

            response.setValue(user.get());
            response.setMessage("Usuario actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que cambia el rol d'un usuari en la base de dades.
     * @param admin boolean que indica si el usuari es administrador o no
     * @param id id de l'usuari
     * @param token token de sessió
     * @return DTO amb un missatge d'actualització
     */
    @Override
    public ResponseEntity<ResponseDTO> updateUserAdmin(boolean admin, UUID id, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta accion");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Usuari> updatedUsuari = usuariRepository.findById(id);
            if (!updatedUsuari.isPresent()) {
                response.setMessage("Usuari no trobat");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            updatedUsuari.get().setIsAdmin(admin);
            usuariRepository.save(updatedUsuari.get());

            response.setValue(updatedUsuari.get());
            response.setMessage("Usuario actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que elimina un usuari en la base de dades.
     * @param id id de l'usuari
     * @param token token de sessió
     * @return DTO amb un missatge de confirmació d'usuari esborrat
     */
    @Override
    public ResponseEntity<ResponseDTO> deleteUser(UUID id, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> updatedUsuari = usuariRepository.findById(id);
            if (!updatedUsuari.isPresent()) {
                response.setMessage("Usuari no trobat");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            usuariRepository.deleteById(id);
            response.setMessage("Usuario eliminado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que obté l'usuari al qual està assignat el token de sessió.
     * @param token token de sessió
     * @return DTO amb el value de l'objecte que conté la informació de l'usuari
     */
    @Override
    public ResponseEntity<ResponseDTO> getUserByToken(String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null || !user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.setValue(UserInfoDTO.fromEntityToDTO(user.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> changeUsuariPassword(String token, UserChangePasswordDTO userChangePasswordDTO) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> foundUser = tokenUtils.getUser(token);
            if (foundUser == null ||!foundUser.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            UserChangePasswordDTO.fromDTOToEntityUpdate(foundUser.get(), userChangePasswordDTO);
            usuariRepository.save(foundUser.get());
            response.setValue(foundUser.get());
            response.setMessage("Contraseña actualitzada");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
