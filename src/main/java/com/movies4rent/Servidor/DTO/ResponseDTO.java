package com.movies4rent.Servidor.DTO;

/**
 * Classe DTO que retorna un valor generic (és a dir que pot admetre objectes entre altres) i un missatge
 * @param <T> el valor generic
 * @author Iván Rodríguez Gómez
 */
public class ResponseDTO<T> {

    private String message;
    private T value;

    /**
     * Getters i setters
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
