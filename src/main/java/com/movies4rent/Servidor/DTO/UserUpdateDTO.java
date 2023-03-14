package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

public class UserUpdateDTO {

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


    public static void fromDTOToEntityUpdate(UserUpdateDTO userUpdateDTO, Usuari usuari) {

        usuari.setNombre(userUpdateDTO.getNombre());
        usuari.setApellidos(userUpdateDTO.getApellidos());
        usuari.setEmail(userUpdateDTO.getCorreo());
        usuari.setDireccion(userUpdateDTO.getAddress());
        usuari.setTelefono(userUpdateDTO.getTelefono());
    }

    public static void updateEntityFromDTO(Usuari userEntity, UserUpdateDTO userDTO) {
        userEntity.setNombre(userDTO.getNombre());
        userEntity.setDireccion(userDTO.getAddress());
        userEntity.setTelefono(userDTO.getTelefono());
        userEntity.setApellidos(userDTO.getApellidos());
        userEntity.setEmail(userDTO.getCorreo());
    }
}
