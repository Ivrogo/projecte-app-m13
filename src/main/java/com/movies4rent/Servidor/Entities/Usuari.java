/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movies4rent.Servidor.Entities;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Classe Entitat que emmagatzema tota la informació relaccionada amb els usuaris
 * @author Iván Rodríguez Gómez
 */
@Entity
@Table(name = "usuaris")
public class Usuari {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String nombre;


    private String apellidos;


    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;


    private String direccion;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isIsAdmin;

    public Usuari() {
    }

    /**
     * Constructor
     * @param nombre, apellidos, telefono, email, direccion, username, password, isIsAdmin
     */
    public Usuari(String nombre, String apellidos, String telefono, String email, String direccion, String username, String password, boolean isIsAdmin) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.username = username;
        this.password = password;
        this.isIsAdmin = isIsAdmin;
    }

    /**
     * Getters i setters
     */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String address) {
        this.direccion = address;
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
    public boolean isIsAdmin() {
        return isIsAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isIsAdmin = admin;
    }
}
