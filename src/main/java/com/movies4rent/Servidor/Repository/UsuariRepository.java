/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Er_jo
 */
@Repository
public interface UsuariRepository extends JpaRepository<Usuari, Long>{
    public Usuari findByUsername(String username);
}
