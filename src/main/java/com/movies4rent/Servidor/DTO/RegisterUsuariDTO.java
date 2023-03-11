/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;
import java.util.UUID;

/**
 *
 * @author Er_jo
 */
public class RegisterUsuariDTO {
    
        private String nombre;
        private String apellidos;
        private String telefono;
        private String email;
        private String address;
        private String username;
        private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    
    
    
    public static Usuari DTOToEntity(RegisterUsuariDTO DTO) {
        
        Usuari usuari = new Usuari();
        usuari.setNombre(usuari.getNombre());
        usuari.setApellidos(usuari.getApellidos());
        usuari.setEmail(usuari.getEmail());
        usuari.setTelefono(usuari.getTelefono());
        usuari.setUsername(usuari.getUsername());
        usuari.setPassword(usuari.getPassword());
        
        return usuari;
        
    }
    
    public static RegisterUsuariDTO entityToDTO(Usuari usuari) {
        
        RegisterUsuariDTO user = new RegisterUsuariDTO();
        
        return user;
        
    }
    
    
}
