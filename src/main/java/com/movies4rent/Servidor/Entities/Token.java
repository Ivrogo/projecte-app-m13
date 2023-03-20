package com.movies4rent.Servidor.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Classe Entitat que inserta tota la informació relacionada amb els tokens de sessió
 * @author Iván Rodríguez Gómez
 */
@Entity
@Table(name = "Token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String username;

    String token;

    LocalDateTime creationDate;

    LocalDateTime lastUsed;

    /**
     * Constructor
     */
    public Token() {
    }

    public Token(UUID id, String username, String token, LocalDateTime creationDate, LocalDateTime lastUsed) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.creationDate = creationDate;
        this.lastUsed = lastUsed;
    }

    /**
     * Getters i setters
     */
    public UUID getId() {
        return id;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

}
