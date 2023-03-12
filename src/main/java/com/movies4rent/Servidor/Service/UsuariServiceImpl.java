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
    public Usuari findByUsername(String username) {
       return repo.findUserByUsername(username);
    }

    @Override
    public ResponseEntity<List<Usuari>> findAll() {
        try {
            List<Usuari> usuaris = repo.findAll();
            return Utils.okStatus();
        } catch (Exception e) {
            return Utils.badRequest();
        }
    }

    @Override
    public ResponseEntity<Usuari> findUserById(Long id) {
        try {
            Optional<Usuari> usuari = repo.findById(id);
            if (!usuari.isPresent()) {
                return Utils.NotFound();
            }
            return Utils.okStatus();
        } catch (Exception e) {
            return Utils.InternalError();
        }

    }

    @Override
    public ResponseEntity updateUser(UserInfoDTO userInfoDTO, Long id) {
        try {
            Optional<Usuari> updatedUsuari = repo.findUserById(id);
            if (!updatedUsuari.isPresent()) {
                return Utils.NotFound();
            }
            UserInfoDTO.fromDTOToEntityUpdate(userInfoDTO, updatedUsuari.get());
            repo.save(updatedUsuari.get());
            return Utils.okStatus();
        } catch (Exception e) {
            return Utils.InternalError();
        }
    }

    @Override
    public ResponseEntity updateUserAdmin(Boolean admin, Long id) {
        try{
            Optional<Usuari> updatedUsuari = repo.findUserById(id);
            if (!updatedUsuari.isPresent()) {
                return Utils.NotFound();
            }
            updatedUsuari.get().setAdmin(admin);
            repo.save(updatedUsuari.get());
            return Utils.okStatus();
        } catch (Exception e) {
            return Utils.InternalError();
        }
    }

    @Override
    public ResponseEntity registerUser(RegisterUserDTO userDTO) {
        try {
            if (userDTO.getPassword().length() < 8) {
                return new ResponseEntity("El limite minimo de caracteres", HttpStatus.BAD_REQUEST);
            }
            repo.save(RegisterUserDTO.fromDTOToEntity(userDTO));
            return Utils.okStatus();
        } catch (Exception e) {
            return Utils.InternalError();
        }
    }

    @Override
    public ResponseEntity deleteUser(Long id) {
        try {
            Optional<Usuari> updatedUsuari = repo.findUserById(id);
            if (!updatedUsuari.isPresent()) {
                return Utils.NotFound();
            }
            repo.deleteById(id);
            return Utils.okStatus();
        } catch (Exception e) {
            return Utils.InternalError();
        }
    }
}
