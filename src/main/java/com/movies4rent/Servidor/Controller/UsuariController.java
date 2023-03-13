package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface UsuariController {

    ResponseEntity<ResponseDTO> getUsuaris(@RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> updateUsuari(@RequestBody UserUpdateDTO userUpdateDTO, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> updateUsuariAdmin(boolean admin, UUID id, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> deleteUsuari(@PathVariable UUID id, @RequestParam(value = "token", required = true) String token);

}
