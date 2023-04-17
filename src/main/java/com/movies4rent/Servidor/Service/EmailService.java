package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.Entities.Alquiler;

public interface EmailService {

    void sendEmail(Alquiler alquiler, String userEmail);

    void sendEmailFinalizacion(Alquiler alquiler, String userEmail);

}
