/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.Model.UsuariModel;
import com.movies4rent.Servidor.Security.BCryptPasswordEncoder;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Er_jo
 */

@RestController
public class UsuariController {
    
    @Autowired
    private UsuariRepository usuariRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public void register(@RequestBody UsuariModel usuari) {
        usuari.setPassword(passwordEncoder.encode(usuari.getPassword()));
    }
}
