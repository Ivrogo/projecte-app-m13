package com.movies4rent.Servidor.Controller;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.UserInfoDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UsuariController {

    ResponseEntity<List<Usuari>> getUsuari();

    ResponseEntity getUsuariById(Long id);

    ResponseEntity RegisterUsuari(@RequestBody RegisterUserDTO userDTO);

    ResponseEntity updateUsuari(@RequestBody UserInfoDTO userInfoDTO, Long id);

    ResponseEntity updateUsuariAdmin(Boolean admin, Long id);

    ResponseEntity deleteUsuari(Long id);

}
