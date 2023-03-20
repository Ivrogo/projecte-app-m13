package com.movies4rent.Servidor.DTO;


/**
 * Classe DTO amb la informació de login d'un usuari
 * @author Iván Rodríguez Gómez
 */
public class UserLoginDTO {

    private String username;
    private String password;

    /**
     * Getter i setters
     */
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

    /**
     * Constructors
     * @param username username de l'usuari
     * @param password password de l'usuari
     */
    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginDTO() {
    }
}
