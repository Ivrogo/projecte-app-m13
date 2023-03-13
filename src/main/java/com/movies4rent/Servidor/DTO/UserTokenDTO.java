package com.movies4rent.Servidor.DTO;

public class UserTokenDTO {

    private String username;
    private String token;
    private String message;


    public UserTokenDTO(String username, String token, String message) {
        this.username = username;
        this.token = token;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserTokenDTO{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
/*
    public void getToken(String username, String password) {
        Usuari usuari =
    }*/
}
