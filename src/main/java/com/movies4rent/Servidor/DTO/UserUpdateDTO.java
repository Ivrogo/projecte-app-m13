package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;


/**
 * Classe DTO que s'encarrega d'enmagatzemar la informació actualitzada d'un usuari
 * @author Iván Rodríguez Gómez
 */
public class UserUpdateDTO {

    private String nombre;
    private String apellidos;
    private String email;
    private String direccion;
    private String telefono;

    /**
     * Getters i setters
     */
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metode DTO que s'encarrega de transformar un DTO a un Usuari (no Implementat)
     * @param userUpdateDTO DTO amb la informació actualitzada
     * @param usuari objecte usuari que rep la informació actualitzada
     */
    public static void fromDTOToEntityUpdate(UserUpdateDTO userUpdateDTO, Usuari usuari) {

        usuari.setNombre(userUpdateDTO.getNombre());
        usuari.setApellidos(userUpdateDTO.getApellidos());
        usuari.setEmail(userUpdateDTO.getEmail());
        usuari.setDireccion(userUpdateDTO.getDireccion());
        usuari.setTelefono(userUpdateDTO.getTelefono());
    }

    /**
     * Metode que s'encarrega d'actualitzar l'entitat a través d'un DTO
     * @param userEntity objecte usuari que rep la informació actualitzada
     * @param userDTO DTO amb la informació actualitzada
     */
    public static void updateEntityFromDTO(Usuari userEntity, UserUpdateDTO userDTO) {
        userEntity.setNombre(userDTO.getNombre());
        userEntity.setDireccion(userDTO.getDireccion());
        userEntity.setTelefono(userDTO.getTelefono());
        userEntity.setApellidos(userDTO.getApellidos());
        userEntity.setEmail(userDTO.getEmail());
    }
}
