package com.movies4rent.Servidor.Service;

import com.github.javafaker.Faker;
import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Entities.Token;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.PasswordEncryptUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RankingServiceTest {


    @Autowired
    private UsuariService usuariService;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UsuariRepository usuariRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PeliculaRepository peliculaRepository;


    /**
     * Neteja la base de dades
     */
    @Test
    @Order(0)
    @DisplayName("Limpia la base de datos")
    public void CleanData() {

        usuariRepository.deleteAll();
        tokenRepository.deleteAll();

    }

    /**
     * Afegim un usuari a la base de dades
     */
    @Test
    @Order(1)
    @DisplayName("Registra un usuario")
    public void AfegimUsuariBaseDades() {
        RegisterUserDTO user = new RegisterUserDTO(
                "demo",
                "demo",
                "demo",
                "demo",
                "demo demo",
                "1234567",
                "demo"
        );

        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("registra un usuario admin")
    public void AfegimAdminBaseDades() {
        Usuari usuari = new Usuari();
        usuari.setUsername("admin");
        usuari.setPassword(PasswordEncryptUtil.encryptPassword("admin"));
        usuari.setEmail("admin");
        usuari.setNombre("admin");
        usuari.setTelefono("123");
        usuari.setApellidos("admin");
        usuari.setDireccion("admin");
        usuari.setIsAdmin(true);

        usuariRepository.save(usuari);
    }

    @Test
    @Order(3)
    @DisplayName("Login")
    public void LoginUsuarioAdminNuevo() {
        String username = "admin";
        String password = "admin";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @Order(4)
    @DisplayName("Login usuario normal")
    public void LoginUsuarioNormalNuevo() {
        String username = "demo";
        String password = "demo";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    @DisplayName("Añadimos peliculas")
    public void addPeliculas() {
        //Creamos 10 peliculas con el mismo director
        List<Pelicula> peliculasSameDirector = IntStream.rangeClosed(1,10)
                .mapToObj(i -> new Pelicula(
                        "demo",
                        "demodirector",
                        "demogenero",
                        new Random().nextInt(75, 180),
                        new Random().nextInt(1990, 2023) ,
                        new Random().nextInt(1, 20),
                        new Random().nextInt(1, 20)
                )).toList();

        peliculaRepository.saveAll(peliculasSameDirector);

        //Creamos 20 peliculas con valores aleatorios
        Faker faker = new Faker();
        List<Pelicula> peliculasRandom = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new Pelicula(
                        faker.name().title(),
                        faker.name().fullName(),
                        faker.book().genre(),
                        faker.number().numberBetween(50, 180),
                        faker.number().numberBetween(1990, 2023),
                        faker.number().numberBetween(1, 20),
                        faker.number().numberBetween(1, 20)
                )).toList();

        peliculaRepository.saveAll(peliculasRandom);
    }

    @Test
    @Order(6)
    @DisplayName("Mostramos el ranking")
    public void MostrarRankingSinFiltros() {

        int page = 0;
        int pageSize = 10;
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = rankingService.getPeliculasRankingFiltered(page, pageSize, "", "", "", null, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(7)
    @DisplayName("Mostramos el ranking filtrado por el mismo director")
    public void MostrarRankingFiltroDirector() {

        int page = 0;
        int pageSize = 10;
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = rankingService.getPeliculasRankingFiltered(page, pageSize, "", "demodirector", "", null, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(8)
    @DisplayName("Mostramos el ranking filtrado por el mismo genero y de un año especifico")
    public void MostrarRankingFiltroAvanzado() {

        int page = 0;
        int pageSize = 10;
        int ano = 1992;
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = rankingService.getPeliculasRankingFiltered(page, pageSize, "", "demodirector", "",  ano, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @Order(9)
    @DisplayName("Mostramos ranking pero el token no existe")
    public void MostrarRankingTokenNotFound() {
        int page = 0;
        int pageSize = 10;
        int ano = 1992;
        String token = "120310";
        ResponseEntity<ResponseDTO> response = rankingService.getPeliculasRankingFiltered(page, pageSize, "", "demodirector", "", null, token);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @Order(10)
    @DisplayName("Mostramos ranking pero esta vacio")
    public void MostrarRankingNotFound() {
        int page = 0;
        int pageSize = 10;

        peliculaRepository.deleteAll();
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = rankingService.getPeliculasRankingFiltered(page, pageSize, null, null, null, null, token.get().getToken());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
