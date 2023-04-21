package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserChangePasswordDTO;
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

/**
 * Clase que conte les proves unitaries per a la clase usuariService
 */
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
    @DisplayName("registra un usuario admin")
    public void AfegimAdminBaseDades() {
        Usuari usuari = new Usuari();
        usuari.setUsername("admin");
        usuari.setPassword("admin");
        usuari.setEmail("admin");
        usuari.setNombre("admin");
        usuari.setTelefono("123");
        usuari.setApellidos("admin");
        usuari.setDireccion("admin");
        usuari.setIsAdmin(true);

        usuariRepository.save(usuari);
    }

    /**
     * loguea un usuari
     */
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

    /**
     * Troba a tots els usuaris registrats
     */
    @Test
    @Order(5)
    @DisplayName("Encuentra todos los usuarios")
    public void TestFindAllUsers() {
        int page = 0;
        int pageSize = 5;
        String username = "";
        String nombre = "";
        String apellidos = "";
        String orden = "";
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.findUsuariFiltered(page, pageSize, nombre, apellidos, username, orden, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    /**
     * Troba a tots els usuaris registrats filtrats per el nombre indicat
     */
    @Test
    @Order(6)
    @DisplayName("Encuentra un usuario por nombre")
    public void TestFindUserByNombre() {
        int page = 0;
        int pageSize = 5;
        String username = "";
        String nombre = "demo";
        String apellidos = "";
        String orden = "";
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.findUsuariFiltered(page, pageSize, nombre, apellidos, username, orden, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Troba a tots els usuaris registrats filtrats per el apellido indicat
     */
    @Test
    @Order(7)
    @DisplayName("Encuentra un usuario por apellidos")
    public void TestFindUserByApellidos() {
        int page = 0;
        int pageSize = 5;
        String username = "";
        String nombre = "";
        String apellidos = "demo";
        String orden = "";
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.findUsuariFiltered(page, pageSize, nombre, apellidos, username, orden, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    /**
     * Troba a tots els usuaris registrats filtrats per el username indicat
     */
    @Test
    @Order(8)
    @DisplayName("Encuentra un usuario por username")
    public void TestFindUserByUsername() {
        int page = 0;
        int pageSize = 5;
        String username = "demo";
        String nombre = "";
        String apellidos = "";
        String orden = "";
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.findUsuariFiltered(page, pageSize, nombre, apellidos, username, orden, token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Troba al usuari propietari del token
     */
    @Test
    @Order(9)
    @DisplayName("Encuentra un usuario por token")
    public void TestFindUserByToken() {

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.getUserByToken(token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    /**
     * Intenta trobar a un usuari per token, pero el token no existeix
     */
    @Test
    @Order(10)
    @DisplayName("Encuentra un usuario por token: no existe token")
    public void TestFindUserByTokenUserDontExist() {
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.getUserByToken("123456");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * Troba un usuari per ID
     */
    @Test
    @Order(11)
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

    /**
     * Intenta trobar a un usuari per id, pero l'id no existeix
     */
    @Test
    @Order(12)
    @DisplayName("Encuentra un usuario por id: no existe usuario")
    public void TestFindUserByIdUserDontExist() {

        Usuari usuari = new Usuari();
        usuari.setId(UUID.randomUUID());

        Optional<Token> token = tokenRepository.findByUsername("demo1");

            ResponseEntity<ResponseDTO> response = usuariService.findById(usuari.getId(), token.get().getToken());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            System.out.println(response.getStatusCode());
    }

    /**
     * Intenta trobar a un usuari per id, pero el token no existeix
     */
    @Test
    @Order(13)
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

    /**
     * Modifica un usuari
     */
    @Test
    @Order(14)
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

    /**
     * Intenta modificar un usuari pero el token no existeix
     */
    @Test
    @Order(15)
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

    /**
     * Modifica el rol d'un usuari per ser administrador
     */
    @Test
    @Order(16)
    @DisplayName("Modifica un usuario para ser administrador")
    public void TestUpdateUserToAdminOk() {
        Usuari usuari = new Usuari();
        usuari.setUsername("user1");
        usuari.setPassword("user1");
        usuari.setEmail("user@user.com");
        usuari.setNombre("usuario");
        usuari.setTelefono("1234567");
        usuari.setApellidos("quiereseradmin");
        usuari.setDireccion("demo");
        usuari.isIsAdmin();

        usuariRepository.save(usuari);

        String username = "admin";
        String password = "admin";

        loginService.login(username, password);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(true, usuari.getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }

    /**
     * Modifica el rol d'un admin per ser usuari
     */
    @Test
    @Order(17)
    @DisplayName("Modifica un administrador para ser usuario")
    public void TestUpdateAdminToUserOk() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("user1");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(false, usuari.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }

    /**
     * Modifica el rol d'un usuari per ser admin: no tiene permisos
     */
    @Test
    @Order(18)
    @DisplayName("modifica el rol d'un usuari per ser admin: no tiene permisos")
    public void TestUpdateUserToAdminPermisosNotOk() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("user1");
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(true, usuari.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }
    /**
     * modifica el rol d'un usuari per ser admin pero el token no existeix.
     */
    @Test
    @Order(19)
    @DisplayName("Modifica un usuario para ser administrador: no existe token")
    public void TestUpdateUserToAdminTokenDontExist() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("user1");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(true, usuari.get().getId(), "213131231");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        System.out.println(response.getStatusCode());

    }

    /**
     * modifica el rol d'un admin per ser usuari pero el token no existeix.
     */
    @Test
    @Order(20)
    @DisplayName("Modifica un administrador para ser usuario: no existe token")
    public void TestUpdateAdminToUserTokenDontExist() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("admin");
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(false, usuari.get().getId(), "213131231");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * modifica el rol d'un usuari per ser admin pero l'usuari no existeix.
     */
    @Test
    @Order(21)
    @DisplayName("modifica un usuario para ser administrador: no existe usuario")
    public void TestUpdateUserToAdminUserDontExist() {

        Optional<Token> token = tokenRepository.findByUsername("admin");
        Usuari usuari = new Usuari();
        usuari.setId(UUID.randomUUID());
        ResponseEntity<ResponseDTO> response = usuariService.updateUserAdmin(true, usuari.getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * Canvia la contraseña d'un usuari demo
     */
    @Test
    @Order(22)
    @DisplayName("Cambio de contraseña de usuario demo")
    public void TestChangePasswordOk() {
        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO();
        userChangePasswordDTO.setPassword("123456");
        userChangePasswordDTO.setConfirmPassword("123456");

        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.changeUsuariPassword(token.get().getToken(), userChangePasswordDTO);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * Canvia la contraseña d'un usuari demo pero el token no existeix
     */
    @Test
    @Order(23)
    @DisplayName("Cambio de contraseña de usuario demo: no existe token")
    public void TestChangePasswordTokenDontExist() {
        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO();
        userChangePasswordDTO.setPassword("123456");
        userChangePasswordDTO.setConfirmPassword("123456");

        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.changeUsuariPassword("123456", userChangePasswordDTO);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * Esborra un usuari
     */
    @Test
    @Order(24)
    @DisplayName("Borra un usuario")
    public void TestDeleteUserOk() {

        Optional<Token> token = tokenRepository.findByUsername("demo");
        Optional<Usuari> usuari = usuariRepository.findUserByUsername("demo");
        ResponseEntity<ResponseDTO> response = usuariService.deleteUser(usuari.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * Intenta esborrar un usuari pero el token no existeix
     */
    @Test
    @Order(25)
    @DisplayName("Borra un usuario: no existe token")
    public void TestDeleteUserTokenDontExist() {

        Optional<Usuari> usuari = usuariRepository.findUserByUsername("demo1");
        ResponseEntity<ResponseDTO> response = usuariService.deleteUser(usuari.get().getId(), "21313123123");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

    /**
     * Intenta esborrar un usuari pero el usuari no existeix
     */
    @Test
    @Order(26)
    @DisplayName("Borra un usuario: no existe usuario")
    public void TestDeleteUserUserDontExist() {

        Optional<Token> token = tokenRepository.findByUsername("demo1");
        Usuari usuari = new Usuari();
        usuari.setId(UUID.randomUUID());
        ResponseEntity<ResponseDTO> response = usuariService.deleteUser(usuari.getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getStatusCode());
    }

}