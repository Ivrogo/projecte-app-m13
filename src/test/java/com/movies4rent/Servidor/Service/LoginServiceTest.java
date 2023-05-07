package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Token;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


/**
 * Classe que conte les proves unitaries per a la classe loginService
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginServiceTest {

    @Autowired
    private LoginService loginService;


    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Neteja les taules de la base de dades
     */
    @Test
    @Order(0)
    @DisplayName("Limpia las tablas")
    public void CleanData() {

        usuariRepository.deleteAll();
        tokenRepository.deleteAll();

    }

    /**
     * Insereix un usuario en la base de datos
     */
    @Test
    @Order(1)
    @DisplayName("Registra un usuario")
    public void TestRegisterUserOK() {
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

    /**
     * Intenta inserir un usuari que existeix a la base de dades
     */
    @Test
    @Order(2)
    @DisplayName("Error registra un usuario existente")
    public void registerUserError() {
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
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }

    /**
     * Intenta inserir un usuari null a la base de dades
     */
    @Test
    @Order(3)
    @DisplayName("Error registra un usuario: null user")
    public void registerUserNull() {
        RegisterUserDTO user = null;

        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Intenta inserir un usuari empty a la base de dades
     */
    @Test
    @Order(4)
    @DisplayName("Error registra un usuario: empty user")
    public void registerUserEmpty() {
        RegisterUserDTO user = new RegisterUserDTO(
                "demo",
                "",
                "demo",
                "demo",
                "demo demo",
                "1234567",
                "demo"
        );
;

        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Intenta inserir un usuari con password null a la base de dades
     */
    @Test
    @Order(5)
    @DisplayName("Error registra un usuario: null password")
    public void registerUserNullPassword() {
        RegisterUserDTO user = new RegisterUserDTO(
                "demo",
                "demo",
                null,
                "demo",
                "demo demo",
                "1234567",
                "demo"
        );


        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }

    /**
     * Intenta inserir un usuari con password empty a la base de dades
     */
    @Test
    @Order(6)
    @DisplayName("Error registra un usuario: empty password")
    public void registerUserEmptyPassword() {
        RegisterUserDTO user = new RegisterUserDTO(
                "demo",
                "demo",
                "",
                "demo",
                "demo demo",
                "1234567",
                "demo"
        );

        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Intenta inserir un usuari con email null a la base de dades
     */
    @Test
    @Order(7)
    @DisplayName("Error registra un usuario: null email")
    public void registerUserNullEmail() {
        RegisterUserDTO user = new RegisterUserDTO(
                null,
                "demo",
                "demo",
                "demo",
                "demo demo",
                "1234567",
                "demo"
        );


        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Intenta inserir un usuari con email empty a la base de dades
     */
    @Test
    @Order(8)
    @DisplayName("Error registra un usuario: empty email")
    public void registerUserEmptyEmail() {
        RegisterUserDTO user = new RegisterUserDTO(
                "",
                "demo",
                "demo",
                "demo",
                "demo demo",
                "1234567",
                "demo"
        );

        ResponseEntity<ResponseDTO> response = loginService.registerUsuari(user);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Logueja a un usuari
     */
    @Test
    @Order(9)
    @DisplayName("loguea un usuario")
    public void testLoginUserOk() {
        String username = "demo";
        String password = "demo";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Intenta loguear a un usuari amb password incorrecta
     */
    @Test
    @Order(10)
    @DisplayName("Error login: not matching")
    public void testLoginUserError() {
        String username = "demo";
        String password = "demo2";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Intenta loguear a un usuari amb username null
     */
    @Test
    @Order(11)
    @DisplayName("Error login: null user")
    public void testLoginUserNull() {
        String username = null;
        String password = "admin1";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Intenta loguear a un usuari amb username empty
     */
    @Test
    @Order(12)
    @DisplayName("Error login: empty user")
    public void testLoginUserEmpty() {
        String username = "";
        String password = "admin1";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    /**
     * Intenta loguear a un usuari amb password null
     */
    @Test
    @Order(13)
    @DisplayName("Error login: null password")
    public void testLoginUserNullPassword() {
        String username = "admin1";
        String password = null;

         ResponseEntity<ResponseDTO> response = loginService.login(username, password);
         Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    /**
     * Intenta loguear a un usuari amb password empty
     */
    @Test
    @Order(14)
    @DisplayName("Error login: empty password")
    public void testLoginUserEmptyPassword() {
        String username = "admin1";
        String password = "";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    /**
     * Realitza logout de un usuari
     */
    @Test
    @Order(15)
    @DisplayName("logout")
    public void logoutUserOk() {
        String username = "demo";

        Optional<Token> token = tokenRepository.findByUsername(username);
        ResponseEntity<ResponseDTO> response = loginService.logout(token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Intenta logout de un usuari amb token null
     */
    @Test
    @Order(17)
    @DisplayName("Error logout: null token")
    public void logoutNullTokenError() {
        String username = "demo";

        Optional<Token> token = tokenRepository.findByUsername(username);
        ResponseEntity<ResponseDTO> response = loginService.logout(null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Intenta logout de un usuari amb token empty
     */
    @Test
    @Order(18)
    @DisplayName("Error logout: empty token")
    public void logoutEmptyTokenError() {
        String username = "demo";

        Optional<Token> token = tokenRepository.findByUsername(username);
        ResponseEntity<ResponseDTO> response = loginService.logout("");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}