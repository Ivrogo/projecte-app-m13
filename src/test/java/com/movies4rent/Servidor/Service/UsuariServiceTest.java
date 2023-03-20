package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import com.movies4rent.Servidor.Entities.Token;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuariServiceTest {

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private TokenRepository tokenRepository;


    @Test
    @Order(0)
    @DisplayName("Limpia la base de datos")
    public void CleanData() {

        usuariRepository.deleteAll();
        tokenRepository.deleteAll();

    }

    @Test
    @Order(1)
    @DisplayName("Registra un usuario")
    public void AfegimUsuariBaseDades() {
        Usuari usuari = new Usuari();
        usuari.setUsername("demo");
        usuari.setPassword("demo");
        usuari.setEmail("demo");
        usuari.setNombre("demo");
        usuari.setTelefono("123");
        usuari.setApellidos("demo");
        usuari.setDireccion("demo");

        usuariRepository.save(usuari);
    }

    @Test
    @Order(2)
    @DisplayName("Login")
    public void LoginUsuarioNuevo() {
        String username = "demo";
        String password = "demo";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Encuentra todos los usuarios")
    public void TestFindAllUsers() {

        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.findAll(token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());


    }
    @Test
    @Order(4)
    @DisplayName("Encuentra un usuario por token")
    public void TestFindUserByToken() {

        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.getUserByToken(token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(5)
    @DisplayName("Encuentra un usuario por token: no existe token")
    public void TestFindUserByTokenUserDontExist() {
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.getUserByToken("123456");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("Encuentra un usuario por id")
    public void TestFindUserById() {
        Usuari usuari = new Usuari();
        usuari.setUsername("demo1");
        usuari.setPassword("demo1");
        usuari.setEmail("demo1");
        usuari.setNombre("demo1");
        usuari.setTelefono("123");
        usuari.setApellidos("demo1");
        usuari.setDireccion("demo1");

        usuariRepository.save(usuari);
        loginService.login(usuari.getUsername(), usuari.getPassword());

        Optional<Token> token = tokenRepository.findByUsername("demo1");
        Optional<Usuari> foundUsuari = usuariRepository.findById(usuari.getId());
        ResponseEntity<ResponseDTO> response = usuariService.findById(foundUsuari.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Encuentra un usuario por id: no existe usuario")
    public void TestFindUserByIdUserDontExist() {

        Usuari usuari = new Usuari();
        usuari.setId(UUID.randomUUID());

        Optional<Token> token = tokenRepository.findByUsername("demo1");

            ResponseEntity<ResponseDTO> response = usuariService.findById(usuari.getId(), token.get().getToken());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            System.out.println(response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Encuentra un usuario por id: no existe token")
    public void TestFindUserByIdTokenDontExist() {
        Usuari usuari = new Usuari();
        usuari.setUsername("demo2");
        usuari.setPassword("demo2");
        usuari.setEmail("demo2");
        usuari.setNombre("demo2");
        usuari.setTelefono("123");
        usuari.setApellidos("demo2");
        usuari.setDireccion("demo2");

        usuariRepository.save(usuari);
        loginService.login(usuari.getUsername(), usuari.getPassword());

        ResponseEntity<ResponseDTO> response = usuariService.findById(usuari.getId(), "21313123123");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(9)
    @DisplayName("Modifica un usuario")
    public void TestUpdateUser() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setApellidos("demodemo");
        userUpdateDTO.setDireccion("demodemo");
        userUpdateDTO.setEmail("demodemo");
        userUpdateDTO.setNombre("demodemo");
        userUpdateDTO.setTelefono("12345667");

        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.updateUser(userUpdateDTO, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());


    }

    @Test
    @Order(10)
    @DisplayName("Modifica un usuario: no existe token")
    public void TestUpdateUserTokenDontExist() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setApellidos("demodemo");
        userUpdateDTO.setDireccion("demodemo");
        userUpdateDTO.setEmail("demodemo");
        userUpdateDTO.setNombre("demodemo");
        userUpdateDTO.setTelefono("12345667");

        ResponseEntity<ResponseDTO> response = usuariService.updateUser(userUpdateDTO, "21313123123");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(11)
    @DisplayName("Modifica un usuario para ser administrador")
    public void TestUpdateUserToAdminOk() {
        Usuari usuari = new Usuari();
        usuari.setUsername("admin");
        usuari.setPassword("admin");
        usuari.setEmail("admin");
        usuari.setNombre("admin");
        usuari.setTelefono("123");
        usuari.setApellidos("admin");
        usuari.setDireccion("admin");
        usuari.isIsAdmin();

        usuariRepository.save(usuari);
        loginService.login(usuari.getUsername(), usuari.getPassword());

        Optional<Token> token = tokenRepository.findByUsername("admin");
        usuari.setAdmin(true);
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(usuari.isIsAdmin(), usuari.getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }
    @Test
    @Order(12)
    @DisplayName("Modifica un administrador para ser usuario")
    public void TestUpdateAdminToUserOk() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("admin");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(false, usuari.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }

    @Test
    @Order(13)
    @DisplayName("Modifica un usuario para ser administrador: no existe token")
    public void TestUpdateUserToAdminTokenDontExist() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(true, usuari.get().getId(), "213131231");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }

    @Test
    @Order(14)
    @DisplayName("Modifica un administrador para ser usuario: no existe token")
    public void TestUpdateAdminToUserTokenDontExist() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(false, usuari.get().getId(), "213131231");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(15)
    @DisplayName("modifica un usuario para ser administrador: no existe usuario")
    public void TestUpdateUserToAdminUserDontExist() {

        Optional<Token> token = tokenRepository.findByUsername("admin");
        Usuari usuari = new Usuari();
        usuari.setId(UUID.randomUUID());
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(true, usuari.getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(16)
    @DisplayName("Borra un usuario")
    public void TestDeleteUserOk() {

        Optional<Token> token = tokenRepository.findByUsername("demo");
        Optional<Usuari> usuari = usuariRepository.findUserByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.deleteUser(usuari.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(17)
    @DisplayName("Borra un usuario: no existe token")
    public void TestDeleteUserTokenDontExist() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("demo1");
        ResponseEntity<ResponseDTO> response = usuariService.deleteUser(usuari.get().getId(), "21313123123");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(18)
    @DisplayName("Borra un usuario: no existe usuario")
    public void TestDeleteUserUserDontExist() {

        Optional<Token> token = tokenRepository.findByUsername("demo1");
        Usuari usuari = new Usuari();
        usuari.setId(UUID.randomUUID());
        ResponseEntity<ResponseDTO> response = usuariService.deleteUser(usuari.getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    @Test
    @Order(19)
    @DisplayName("Limpia la base de datos")
    public void ClearDataBase() {

        usuariRepository.deleteAll();
        tokenRepository.deleteAll();
    }
}