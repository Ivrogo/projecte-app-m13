/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * Classe interficie per els metodes de gestio de l'usuari
 * @author Iván Rodríguez Gómez
 */
public interface UsuariService {

    ResponseEntity<ResponseDTO> findAll(String token);

    ResponseEntity<ResponseDTO> findById(UUID id, String token);

    ResponseEntity<ResponseDTO> updateUser(UserUpdateDTO userUpdateDTO, String token);

    ResponseEntity<ResponseDTO> updateUserAdmin(boolean admin, UUID id, String token);

    ResponseEntity<ResponseDTO> deleteUser(UUID id, String token);

    ResponseEntity<ResponseDTO> getUserByToken(String token);
}
