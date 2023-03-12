/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.UserInfoDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 *
 * @author Er_jo
 */
public interface UsuariService {
         Usuari findByUsername(String username);

         ResponseEntity<List<Usuari>> findAll();
        ResponseEntity<Usuari> findUserById(Long id);

        ResponseEntity updateUser (UserInfoDTO userInfoDTO, Long id);

         ResponseEntity updateUserAdmin (Boolean admin, Long id);

         ResponseEntity registerUser(RegisterUserDTO userDTO);

         ResponseEntity deleteUser(Long id);


}
