/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserInfoDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 *
 * @author Er_jo
 */
public interface UsuariService {
         ResponseEntity<List<Usuari>> findByUsername(String username);

         ResponseEntity<ResponseDTO> findByUsernameAndPassword(String username, String password);

         ResponseEntity<List<Usuari>> findAll();
        <T>ResponseEntity<T> findUserById(Long id);

        <T>ResponseEntity<T> updateUser (UserInfoDTO userInfoDTO, Long id);

         <T>ResponseEntity<T> updateUserAdmin (Boolean admin, Long id);

         <T>ResponseEntity<T> registerUser(RegisterUserDTO userDTO);

         <T>ResponseEntity<T> deleteUser(Long id);


}
