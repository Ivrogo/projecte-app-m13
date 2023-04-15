package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

import java.util.function.Predicate;

public class UserFilterDTO {

    private String nombre;

    private String apellidos;

    private String username;



    public UserFilterDTO(String nombre, String apellidos, String username) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
    }

    public Predicate<Usuari> getPredicate() {
        Predicate<Usuari> predicate = usuari-> true;
        if (nombre != null) {
            predicate = predicate.and(usuari -> usuari.getNombre().equals(nombre));
        }
        if (apellidos != null) {
            predicate = predicate.and(usuari -> usuari.getApellidos().equals(apellidos));
        }
        if (username != null) {
            predicate = predicate.and(usuari -> usuari.getUsername().equals(username));
        }
        return predicate;
    }
}
