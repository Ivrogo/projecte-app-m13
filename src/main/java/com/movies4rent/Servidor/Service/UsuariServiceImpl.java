package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.UserInfoDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository repo;

    @Override
    public ResponseEntity<List<Usuari>> findByUsername(String username) {
        try {
            List<Usuari> usuaris = repo.findUserByUsername(username);
            return Utils.okStatus(usuaris);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<Usuari>> findAll() {
        try {
            List<Usuari> usuaris = repo.findAll();
            return Utils.okStatus(usuaris);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public <T>ResponseEntity<T> findUserById(Long id) {
        try {
            Optional<Usuari> usuari = repo.findById(id);
            if (!usuari.isPresent()) {
                return (ResponseEntity<T>) Utils.NotFound("l'usuari no existeix");
            }
            return (ResponseEntity<T>) Utils.okStatus(usuari);
        } catch (Exception e) {
            return (ResponseEntity<T>) Utils.InternalError("Error s'ha trobat una excepció");
        }

    }

    @Override
    public <T>ResponseEntity<T> updateUser(UserInfoDTO userInfoDTO, Long id) {
        try {
            Optional<Usuari> updatedUsuari = repo.findUserById(id);
            if (!updatedUsuari.isPresent()) {
                return (ResponseEntity<T>) Utils.NotFound("Usuari no trobat");
            }
            UserInfoDTO.fromDTOToEntityUpdate(userInfoDTO, updatedUsuari.get());
            repo.save(updatedUsuari.get());
            return (ResponseEntity<T>) Utils.okStatus(updatedUsuari);
        } catch (Exception e) {
            return (ResponseEntity<T>) Utils.InternalError("Error Excepció trobada");
        }
    }

    @Override
    public <T>ResponseEntity<T> updateUserAdmin(Boolean admin, Long id) {
        try{
            Optional<Usuari> updatedUsuari = repo.findUserById(id);
            if (!updatedUsuari.isPresent()) {
                return (ResponseEntity<T>) Utils.NotFound("Usuari no trobat");
            }
            updatedUsuari.get().setAdmin(admin);
            repo.save(updatedUsuari.get());
            return (ResponseEntity<T>) Utils.okStatus("OK");
        } catch (Exception e) {
            return (ResponseEntity<T>) Utils.InternalError("Error Excepció trobada");
        }
    }

    @Override
    public <T>ResponseEntity<T> registerUser(RegisterUserDTO userDTO) {
        try {
            repo.save(RegisterUserDTO.fromDTOToEntity(userDTO));
            return (ResponseEntity<T>) Utils.okStatus("OK");
        } catch (Exception e) {
            return (ResponseEntity<T>) Utils.InternalError("Error excepció trobada");
        }
    }

    @Override
    public <T>ResponseEntity<T> deleteUser(Long id) {
        try {
            Optional<Usuari> updatedUsuari = repo.findUserById(id);
            if (!updatedUsuari.isPresent()) {
                return (ResponseEntity<T>) Utils.NotFound("Usuari no trobat");
            }
            repo.deleteById(id);
            return (ResponseEntity<T>) Utils.okStatus("OK");
        } catch (Exception e) {
            return (ResponseEntity<T>) Utils.InternalError("Error excepcio trobada");
        }
    }
}
