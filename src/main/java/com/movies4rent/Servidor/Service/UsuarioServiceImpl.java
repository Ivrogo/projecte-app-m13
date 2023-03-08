/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Er_jo
 */
@Service
public class UsuarioServiceImpl implements UsuariService {
    
    @Autowired
    private UsuariRepository repository;

    @Override
    public Usuari findByUsername(String username) {
        return repository.findByUsername(username);
    }
    
}
