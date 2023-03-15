package com.movies4rent.Servidor.DTO;

public class LoginTokenDTO {

    private String token;
    private boolean isIsAdmin;

    public LoginTokenDTO(String token, boolean isIsAdmin) {
        this.token = token;
        this.isIsAdmin = isIsAdmin;
    }

    public boolean isIsAdmin() {
        return isIsAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isIsAdmin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
