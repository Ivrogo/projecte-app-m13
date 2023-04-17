/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Classe Interficie que emmagatzema tots el metodes del repositori JPA referents als usuaris. Implementa les cerques específiques
 * que utilitzen la classe usuariServei.
 * @author Iván Rodríguez Gómez
 */
@Repository
public interface UsuariRepository extends JpaRepository<Usuari, UUID> {

    Page<Usuari> findAll(Pageable pageable);

    public Optional<Usuari> findUserByUsername(String username);

    public Optional<Usuari> findUserByUsernameAndPassword(String username, String password);

}
