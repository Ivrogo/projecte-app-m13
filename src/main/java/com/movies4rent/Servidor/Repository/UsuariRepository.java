/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Er_jo
 */
@Repository
public interface UsuariRepository extends JpaRepository<Usuari, Long>{

    public Optional<Usuari> findUserById(Long id);

    public List<Usuari> findUserByUsername(String username);

}
