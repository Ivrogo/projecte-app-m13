package com.movies4rent.Servidor.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Classe Utilitats per no tener que escriure els HTTPStatus
 * @author Iván Rodríguez Gómez
 */
public class Utils {

    public static boolean isNullOrEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static <T>ResponseEntity<T> badRequest(T value) {
        return new ResponseEntity(value, HttpStatus.BAD_REQUEST);
    }

    public static <T>ResponseEntity<T> okStatus(T value) {
        return new ResponseEntity(value, HttpStatus.OK);
    }

    public static <T>ResponseEntity<T> NotFound(T value) {
        return new ResponseEntity(value, HttpStatus.NOT_FOUND);
    }

    public static <T>ResponseEntity<T> InternalError(T value) {
        return new <T>ResponseEntity<T>(value, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
