package com.movies4rent.Servidor.Controller;


import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import com.movies4rent.Servidor.Service.UsuariService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsuariControllerImpl implements UsuariController {

    @Autowired
    private UsuariService usuariService;

    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> getUsuaris(@RequestParam(value = "token", required = true) String token) {
        return usuariService.findAll(token);
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUsuari(@RequestBody UserUpdateDTO userUpdateDTO, @RequestParam(value = "token", required = true) String token) {
        return usuariService.updateUser(userUpdateDTO, token);
    }


    @PutMapping("/update/{id}/{admin}")
    public ResponseEntity<ResponseDTO> updateUsuariAdmin(@PathParam("admin") boolean admin, @PathParam("id") UUID id, @RequestParam(value = "token", required = true) String token) {
        return usuariService.updateUserAdmin(admin, id, token);
    }

}
