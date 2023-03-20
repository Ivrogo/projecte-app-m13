package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserLoginDTO;
import com.movies4rent.Servidor.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Classe responsable de controlar la logica de login, logout i registre
 * @author Ivan Rodriguez Gomez
 */
@RestController
@RequestMapping()
public class LoginControllerImpl implements LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Metode login: Rep un DTO amb l'usuari i contrasenya i retorna un DTO de resposta amb un token
     * @param userLoginDTO DTO amb l'usuari i contrasenya
     * @Return metode de login de loginService
     */
    @Override
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        return loginService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
    }

    /**
     * Metode logout: Rep un token de sessió i retorna un tancament de sesió
     * @param token Token de sessió
     * @return metode de logout de loginService
     */
    @Override
    @GetMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(@RequestParam(value = "token", required = true) String token) {
        return loginService.logout(token);
    }

    /**
     * Metode register: Rep un DTO amb la informació bàsica de registre (nom, cognom, email, direccio, usuari i contrasenya) i retorna un DTO de resposta
     * confirmant que l'usuari ha estat registrat correctament.
     * @param userDTO DTO amb la informació bàsica de registre
     * @return metode de register de loginService
     */
    @Override
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUsuari(@RequestBody RegisterUserDTO userDTO) {
        return loginService.registerUsuari(userDTO);
    }
}
