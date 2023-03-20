package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * Classe interficie per als metodes de gestió de login,logout i registració
 * @author Iván Rodríguez Gómez
 */
public interface LoginService {

    ResponseEntity<ResponseDTO> login(String username, String password) ;

    ResponseEntity<ResponseDTO> logout(String token);

    ResponseEntity<ResponseDTO> registerUsuari(RegisterUserDTO userDTO);
}
