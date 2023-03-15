package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

import java.util.UUID;

public class UserInfoDTO {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String email;
    private String direccion;

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
