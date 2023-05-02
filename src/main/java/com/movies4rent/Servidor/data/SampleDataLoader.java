package com.movies4rent.Servidor.data;

import com.github.javafaker.Faker;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Service.LoginService;
import com.movies4rent.Servidor.Utils.PasswordEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Clase component per la carrega de dades de proba dins de la base de dades.(NOMES UTILITZAT PER PROVES)
 * @author Ivan Rodriguez Gomez.
 */
@Component
public class SampleDataLoader implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(SampleDataLoader.class);
    private final LoginService loginService;

    private final PasswordEncryptUtil passwordEncryptUtil;

    private final UsuariRepository userRepository;

    private final PeliculaRepository pelRepository;
    private final Faker faker;

    public SampleDataLoader(LoginService loginService, PasswordEncryptUtil passwordEncryptUtil, UsuariRepository userRepository, PeliculaRepository pelRepository) {
        this.loginService = loginService;
        this.passwordEncryptUtil = passwordEncryptUtil;
        this.userRepository = userRepository;
        this.pelRepository = pelRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading sample data...");

        Usuari usuari = new Usuari();
        usuari.setNombre("admin");
        usuari.setApellidos("admin admin");
        usuari.setTelefono("12345678");
        usuari.setEmail("saoyara@gmail.com");
        usuari.setDireccion("demo");
        usuari.setUsername("admin");
        usuari.setPassword(PasswordEncryptUtil.encryptPassword("admin"));
        usuari.setIsAdmin(true);


        userRepository.save(usuari);


        //Creamos 50 usuarios
        List<Usuari> users = IntStream.rangeClosed(1,50)
                .mapToObj(i -> new Usuari(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.phoneNumber().cellPhone(),
                        faker.internet().emailAddress(),
                        faker.address().streetAddress(),
                        faker.name().username(),
                        PasswordEncryptUtil.encryptPassword(faker.internet().password()),
                        false
                ))
                .toList();

        userRepository.saveAll(users);


        //Creamos 10 peliculas con el mismo director
        List<Pelicula> peliculasOfSameDirector = IntStream.rangeClosed(1,10)
                .mapToObj(i -> new Pelicula(
                        faker.name().title(),
                        "demodirector",
                        faker.book().genre(),
                        faker.number().numberBetween(50, 180),
                        faker.number().numberBetween(1990,2023),
                        faker.number().numberBetween(1,20),
                        faker.number().numberBetween(1, 20)
                )).toList();

        pelRepository.saveAll(peliculasOfSameDirector);



        //Creamos 50 peliculas con datos random
        List<Pelicula> peliculas = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> new Pelicula(
                        faker.name().title(),
                        faker.name().fullName(),
                        faker.book().genre(),
                        faker.number().numberBetween(50, 180),
                        faker.number().numberBetween(1990, 2023),
                        faker.number().numberBetween(1, 20),
                        faker.number().numberBetween(1, 20)
                )).toList();

        pelRepository.saveAll(peliculas);
    }

}
