package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginController {

    ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) throws Exception;

    void logout(HttpServletResponse response);

    Object isValid(HttpServletRequest request, HttpServletResponse response);

}
