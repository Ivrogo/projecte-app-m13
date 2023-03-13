package com.movies4rent.Servidor.Controller;


import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.UserInfoDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Service.UsuariService;
import com.movies4rent.Servidor.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuariControllerImpl {

    @Autowired
    private UsuariService service;


    @GetMapping()
    public ResponseEntity<List<Usuari>> getUsuari() {
       return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUsuariById(Long id) {
       return service.findUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity RegisterUsuari(@RequestBody RegisterUserDTO userDTO) {
        if (userDTO == null) {
            return Utils.badRequest("L'objecte no existeix");
        }
        if (Utils.isNullOrEmpty(userDTO.getEmail())) {
            return Utils.badRequest("Error falta l'email");
        }
        if (Utils.isNullOrEmpty(userDTO.getPassword())){
            return Utils.badRequest("Error falta la contrasenya");
        }
        if (Utils.isNullOrEmpty(userDTO.getUsername())){
            return Utils.badRequest("Error falta l'usuari");
        }
        return service.registerUser(userDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUsuari(@RequestBody UserInfoDTO userInfoDTO, Long id) {
        if (userInfoDTO == null) {
            return Utils.badRequest("l'objecte esta no existeix");
        }
        return service.updateUser(userInfoDTO, id);
    }

    @PutMapping("/update/{id}/{admin}")
    public ResponseEntity updateUsuariAdmin(Boolean admin, Long id) {
        return service.updateUserAdmin(admin, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUsuari(Long id) {
        return service.deleteUser(id);
    }
 }
