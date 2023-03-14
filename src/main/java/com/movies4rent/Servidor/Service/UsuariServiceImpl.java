package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.GetUsuariDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
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

@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public ResponseEntity<ResponseDTO> findAll(String token) {
        ResponseDTO<List<GetUsuariDTO>> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            List<Usuari> usuaris = usuariRepository.findAll();
            List<GetUsuariDTO> usuarisDTO = new ArrayList<>();
            usuaris.forEach(x -> usuarisDTO.add(GetUsuariDTO.fromEntityToDTO(x)));
            if (usuaris.size() <= 0) {
                response.setMessage("No hay usuarios");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setValue(usuarisDTO);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> findById(UUID id, String token) {
        ResponseDTO<GetUsuariDTO> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null ||!user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Usuari> foundUsuari = usuariRepository.findById(id);
            if (!foundUsuari.isPresent()) {
                response.setMessage("Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setValue(GetUsuariDTO.fromEntityToDTO(foundUsuari.get()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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

            response.setMessage("Usuario actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUserAdmin(boolean admin, UUID id, String token) {
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

            response.setMessage("Usuario actualizado");
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
            response.setMessage("Usuario eliminado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
