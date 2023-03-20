package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

import java.util.UUID;

/**
 * Classe DTO que conte la informació d'un usuari
 * @author Iván Rodríguez Gómez
 */
public class UserInfoDTO {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String email;
    private String direccion;

    /**
     * Getters i Setters
     */
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Metode per transformar una entitat a un DTO
     * @param usuari Objecte usuari
     * @return DTO amb la informació extreta de l'objecte usuari
     */
    public static UserInfoDTO fromEntityToDTO(Usuari usuari) {
        UserInfoDTO userInfo = new UserInfoDTO();

        userInfo.setId(usuari.getId());
        userInfo.setNombre(usuari.getNombre());
        userInfo.setApellidos(usuari.getApellidos());
        userInfo.setEmail(usuari.getEmail());
        userInfo.setDireccion(usuari.getDireccion());

        return userInfo;

    }
}
