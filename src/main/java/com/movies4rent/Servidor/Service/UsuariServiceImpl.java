package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public ResponseEntity<ResponseDTO> findAll(String token) {
        ResponseDTO<List<Usuari>> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            List<Usuari> usuaris = usuariRepository.findAll();
            if (usuaris.size() <= 0) {
                response.setMessage("No hay usuarios");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setValue(usuaris);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUserAdmin(Boolean admin, UUID id, String token) {
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

            updatedUsuari.get().setAdmin(admin);
            usuariRepository.save(updatedUsuari.get());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
