package com.movies4rent.Servidor.DTO;

public class ResponseDTO<T> {

    private String message;
    private T value;

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
