package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<ResponseDTO> login(String username, String password) throws Exception;

    ResponseEntity<ResponseDTO> logout(String token);

    ResponseEntity<ResponseDTO> registerUsuari(RegisterUserDTO userDTO);
}
