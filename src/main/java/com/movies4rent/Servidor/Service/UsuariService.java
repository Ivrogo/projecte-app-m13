/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.Entities.Usuari;

/**
 *
 * @author Er_jo
 */
public interface UsuariService {
        public Usuari findByUsername(String username);
}
