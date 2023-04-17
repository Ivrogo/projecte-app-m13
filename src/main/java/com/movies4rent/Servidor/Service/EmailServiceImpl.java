package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Value("${email.sender}")
    private String emailUser;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    public void sendEmail(Alquiler alquiler, String userEmail) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailUser);
        message.setTo(userEmail);
        message.setSubject("Confirmación de alquiler");
        message.setText("Hola " + usuariRepository.findById(alquiler.getUsuari()).get().getNombre() + ",\n\n"
                + "Este correo es para confirmar que has alquilado la película " + peliculaRepository.findById(alquiler.getPelicula()).get().getTitulo()
                + ". El alquiler ha sido registrado correctamente en nuestro sistema y podrás disfrutar de la película"
                + " hasta la fecha de vencimiento: " + alquiler.getFechaFin() + ".\n\n"
                + "Gracias por utilizar nuestro servicio.\n\n"
                + "Saludos cordiales,\n\n"
                + "Movies4Rent.");

        mailSender.send(message);
    }

    @Override
    public void sendEmailFinalizacion(Alquiler alquiler, String userEmail) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailUser);
        message.setTo(userEmail);
        message.setSubject("Finalización de alquiler");
        message.setText("Hola " + usuariRepository.findById(alquiler.getUsuari()).get().getNombre() + ",\n\n"
                + "Este correo es para notificarte que el plazo de tu alquiler ha finalizado.\n\n"
                + "Gracias por utilizar nuestro servicio.\n\n"
                + "Saludos cordiales,\n\n"
                + "Movies4Rent.");

        mailSender.send(message);
    }
}
