package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserChangePasswordDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Classe interficie per al controlador de l'usuari
 * @author Iván Rodríguez Gómez
 */
public interface UsuariController {

    ResponseEntity<ResponseDTO> getUsuarisFiltered(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidos, @RequestParam(required = false) String username,
                                                   @RequestParam(required = false) String orden, @RequestParam(value = "token", required = true) String token);
    ResponseEntity<ResponseDTO> getUsuari(@PathVariable UUID id, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> updateUsuari(@RequestBody UserUpdateDTO userUpdateDTO, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> updateUsuariAdmin(boolean admin, UUID id, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> deleteUsuari(@PathVariable UUID id, @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> getInfoUsuariByToken(@PathVariable @RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> changeUsuariPassword(@RequestParam(value = "token", required = true) String token, @RequestBody UserChangePasswordDTO userChangePasswordDTO);

}
