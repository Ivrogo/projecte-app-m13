package com.movies4rent.Servidor.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {

    public static boolean isNullOrEmpty(String param) {
        return param == null || param.trim().length() == 0;
    }

    public static ResponseEntity badRequest() {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity okStatus() {
        return new ResponseEntity(HttpStatus.OK);
    }

    public static ResponseEntity NotFound() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity InternalError() {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
