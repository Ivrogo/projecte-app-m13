package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.UserTokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public void doLogin(String username, String password) {

    }

    @Override
    public ResponseEntity<UserTokenDTO> loginUser() {
        return null;
    }
}
