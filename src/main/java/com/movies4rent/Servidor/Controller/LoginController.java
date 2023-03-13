package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface LoginController {

    ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) throws Exception;

    ResponseEntity<ResponseDTO> logout(@RequestParam(value = "token", required = true) String token);

    ResponseEntity<ResponseDTO> registerUsuari(@RequestBody RegisterUserDTO userDTO);

}
