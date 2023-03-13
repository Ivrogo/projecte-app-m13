package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserLoginDTO;
import com.movies4rent.Servidor.Service.UsuariService;
import com.movies4rent.Servidor.Utils.EncoderUtil;
import com.movies4rent.Servidor.Utils.Helpers;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginControllerImpl implements LoginController {

    @Autowired
    private UsuariService service;

    @Autowired
    private Helpers utilities;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(UserLoginDTO userLoginDTO, HttpServletResponse response) throws Exception{
        System.out.println(userLoginDTO.getPassword());
        System.out.println(userLoginDTO.getUsername());
        ResponseEntity<ResponseDTO> response1= service.findByUsernameAndPassword(userLoginDTO.getUsername(), EncoderUtil.getSHA256(userLoginDTO.getPassword()));
        if (response1 != null) {
            String token = utilities.createToken(response1.getBody(), userLoginDTO.getRememberMe());
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            if (userLoginDTO.getRememberMe()) {
                cookie.setMaxAge(7 * 24 * 60 * 60);
            } else {
                cookie.setMaxAge(2 * 60 * 60);
            }
            response.addCookie(cookie);
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
        return response1;
    }

    @Override
    public void logout(HttpServletResponse response) {

    }

    @Override
    public Object isValid(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
