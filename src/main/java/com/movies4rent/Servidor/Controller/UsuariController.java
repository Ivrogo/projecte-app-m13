package com.movies4rent.Servidor.Controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
