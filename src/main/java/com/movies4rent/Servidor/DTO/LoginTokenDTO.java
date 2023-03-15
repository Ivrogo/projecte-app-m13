package com.movies4rent.Servidor.DTO;

public class LoginTokenDTO {

    private String token;
    private boolean isAdmin;

    public LoginTokenDTO(String token, boolean isAdmin) {
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
