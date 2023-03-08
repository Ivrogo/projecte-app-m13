package com.movies4rent.Servidor.Controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Er_jo
 */
@RestController
public class UsuariController {
    
    @GetMapping("/demo")
    public String demo() {
        return "hola mundo";
    }
 
    
}
