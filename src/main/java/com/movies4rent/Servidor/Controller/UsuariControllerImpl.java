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
            return Utils.badRequest();
        }
        if (Utils.isNullOrEmpty(userDTO.getEmail()) || Utils.isNullOrEmpty(userDTO.getPassword()) || Utils.isNullOrEmpty(userDTO.getUsername())) {
            return Utils.badRequest();
        }
        return service.registerUser(userDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUsuari(@RequestBody UserInfoDTO userInfoDTO, Long id) {
        if (userInfoDTO == null) {
            return Utils.badRequest();
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
