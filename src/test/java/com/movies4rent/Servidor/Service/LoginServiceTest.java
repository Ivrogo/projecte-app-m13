package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private UsuariRepository usuariRepository;

    @BeforeEach
    void setUp() {
        Usuari usuari = new Usuari();
        usuari.setDireccion("demo");
        usuari.setUsername("admin1");
        usuari.setPassword("admin1");
        usuari.setEmail("demo");
        usuari.setNombre("demo");
        usuari.setTelefono("123");
        usuari.setApellidos("demo");

        Mockito.when(usuariRepository.findUserByUsername("admin1")).thenReturn(Optional.of(usuari));
    }
    @Test
    public void testLoginUserOk() {
        String username = "admin1";
        String password = "admin1";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("ok");
    }
    @Test
    public void testLoginUserError() {
        String username = "admin1";
        String password = "admin2";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("ok");
    }
    @Test
    public void testLoginUserNull() {
        String username = null;
        String password = "admin1";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Usuari null");

    }
    @Test
    public void testLoginUserEmpty() {
        String username = "";
        String password = "admin1";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Usuari empty");

    }
    @Test
    public void testLoginUserNullPassword() {
        String username = "admin1";
        String password = null;

         ResponseEntity<ResponseDTO> response = loginService.login(username, password);
         Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
         System.out.println("Password null");

    }
    @Test
    public void testLoginUserEmptyPassword() {
        String username = "admin1";
        String password = "";

        ResponseEntity<ResponseDTO> response = loginService.login(username, password);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Password empty");

    }
    @Test
    public void registerUserOk() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("demo");
        user.setPassword("demo");
        user.setEmail("demo");
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("success", HttpStatus.OK);
            System.out.println("ok");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerUserError() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("demo");
        user.setPassword("demo");
        user.setEmail("demo");
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("usuari ja registrat");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerUserNull() {
        RegisterUserDTO user = null;
        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("user null");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerUserEmpty() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("");
        user.setPassword("demo");
        user.setEmail("demo");
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("user empty");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerUserNullPassword() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("demo");
        user.setPassword(null);
        user.setEmail("demo");
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("password null");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerUserEmptyPassword() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("demo");
        user.setPassword("");
        user.setEmail("demo");
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("password empty");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void registerUserNullEmail() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("demo");
        user.setPassword("demo");
        user.setEmail(null);
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("email null");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerUserEmptyEmail() {
        RegisterUserDTO user = new RegisterUserDTO();
        user.setDireccion("demo");
        user.setUsername("demo");
        user.setPassword("demo");
        user.setEmail("");
        user.setNombre("demo");
        user.setTelefono("123");
        user.setApellidos("demo");

        try {
            loginService.registerUsuari(user);
            new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
            System.out.println("email empty");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void logoutUserOk() {

    }
}