package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserLoginDTO;
import com.movies4rent.Servidor.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginControllerImpl implements LoginController {

    @Autowired
    private LoginService loginService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        return loginService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
    }

    @Override
    @GetMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(@RequestParam(value = "token", required = true) String token) {
        return loginService.logout(token);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUsuari(@RequestBody RegisterUserDTO userDTO) {
        return loginService.registerUsuari(userDTO);
    }
}
