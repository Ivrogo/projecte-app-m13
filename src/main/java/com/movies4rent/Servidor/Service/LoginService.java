package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.UserTokenDTO;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    void doLogin(String username, String password);
    ResponseEntity<UserTokenDTO> loginUser();
}
