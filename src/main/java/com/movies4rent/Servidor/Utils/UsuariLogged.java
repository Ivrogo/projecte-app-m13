package com.movies4rent.Servidor.Utils;

public class UsuariLogged {

    private static final ThreadLocal<String> usuariHolder = new ThreadLocal<>();

    public static void logIn(String usuari) {
        usuariHolder.set(usuari);
    }

    public static void logOut() {
        usuariHolder.remove();
    }

    public static String get() {
        return usuariHolder.get();
    }
}
