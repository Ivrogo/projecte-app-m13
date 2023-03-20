package com.movies4rent.Servidor.DTO;


/**
 * Classe DTO amb el token i el rol de l'usuari logueat
 * @author Iván Rodríguez Gómez
 */
public class LoginTokenDTO {

    private String token;
    private boolean isAdmin;

    /**
     * Constructor de la classe
     * @param token
     * @param isAdmin
     */
    public LoginTokenDTO(String token, boolean isAdmin) {
        this.token = token;
        this.isAdmin = isAdmin;
    }

    /**
     * Getters i setters
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
