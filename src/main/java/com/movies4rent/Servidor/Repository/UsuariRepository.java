/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Model.UsuariModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Er_jo
 */
public interface UsuariRepository extends JpaRepository<UsuariModel, Long>{
    
    Optional<UsuariModel> findByUsername(String username);
}
