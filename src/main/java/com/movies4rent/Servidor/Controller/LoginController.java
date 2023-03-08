/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Er_jo
 */
@RestController
public class LoginController {
    
    @Autowired
    private UsuariService usuariService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuari usuari) {
        Usuari foundUsuari = usuariService.findByUsername(usuari.getUsername());
        if (foundUsuari == null || !foundUsuari.getPassword().equals(usuari.getPassword())){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
        return ResponseEntity.ok().build();
    }
}
