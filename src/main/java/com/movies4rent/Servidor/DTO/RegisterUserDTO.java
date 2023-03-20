package com.movies4rent.Servidor.DTO;


import com.movies4rent.Servidor.Entities.Usuari;

/**
 * Classe DTO per obtenir la informació d'un usuari que es registra.
 * @author Iván Rodríguez Gómez
 */
public class RegisterUserDTO {

    private String email;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;


    /**
     * Metode per transformar un DTO a un objecte usuari
     * @param userDTO DTO amb les dades de l'usuari a transformar
     * @return objecte usuari
     */
    public static Usuari fromDTOToEntity(RegisterUserDTO userDTO) {

        Usuari user = new Usuari();
        user.setUsername(userDTO.getUsername());
        user.setAdmin(false);
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setNombre(userDTO.getNombre());
        user.setApellidos(userDTO.getApellidos());
        user.setTelefono(userDTO.getTelefono());
        user.setDireccion(userDTO.getDireccion());

        return user;
    }

    /**
     * Getters i Setters
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
