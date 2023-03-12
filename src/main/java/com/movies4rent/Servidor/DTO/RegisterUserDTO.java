package com.movies4rent.Servidor.DTO;


import com.movies4rent.Servidor.Entities.Usuari;

public class RegisterUserDTO {

    private String email;

    private String nombre;

    private String apellidos;

    private String username;

    private String password;


    public static Usuari fromDTOToEntity(RegisterUserDTO userDTO) {

        Usuari user = new Usuari();
        user.setUsername(userDTO.getUsername());
        user.setAdmin(false);
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        return user;
    }

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
}
