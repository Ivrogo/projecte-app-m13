package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

public class UserInfoDTO {

    private String nombre;

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String apellidos;

    private String correo;

    private String address;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private String telefono;


    public static void fromDTOToEntityUpdate(UserInfoDTO userInfoDTO, Usuari usuari) {

        usuari.setNombre(userInfoDTO.getNombre());
        usuari.setApellidos(userInfoDTO.getApellidos());
        usuari.setEmail(userInfoDTO.getCorreo());
        usuari.setAddress(userInfoDTO.getAddress());
        usuari.setTelefono(userInfoDTO.getTelefono());
    }
}
