package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Alquiler;
import com.movies4rent.Servidor.Entities.Pelicula;
import com.movies4rent.Servidor.Entities.Token;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.AlquilerRepository;
import com.movies4rent.Servidor.Repository.PeliculaRepository;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.EstadoAlquiler;
import com.movies4rent.Servidor.Utils.PasswordEncryptUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlquilerServiceTest {


    @Autowired
    private UsuariService usuariService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

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
                "saoyara@gmail.com",
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

    @Test
    @Order(5)
    @DisplayName("Afegim una pelicula")
    public void AfegimPeliculaNuevo() {

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Titulo");
        pelicula.setDirector("Director");
        pelicula.setGenero("Genero");
        pelicula.setAño(2020);
        pelicula.setDuracion(100);
        pelicula.setPrecio(10);


        peliculaRepository.save(pelicula);
    }

    @Test
    @Order(6)
    @DisplayName("Afegim un alquiler")
    public void AfegimAlquilerNuevo() {

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("Titulo");
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");
        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");

        ResponseEntity<ResponseDTO> response = alquilerService.crearAlquiler(foundPelicula.get().getId(), foundUsuari.get().getId(), foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(7)
    @DisplayName("Afegim un alquiler: una de las ids es null")
    public void AfegimAlquilerNuevo2() {

        UUID peliculaId = null;
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");
        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");

        ResponseEntity<ResponseDTO> response = alquilerService.crearAlquiler(peliculaId, foundUsuari.get().getId(), foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }

    @Test
    @Order(8)
    @DisplayName("Mostramos los alquileres")
    public void MostramosAlquileres() {

        Optional<Token> foundToken = tokenRepository.findByUsername("admin");

        UUID peliculaId = null;
        UUID usuariId = null;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        Integer precio = null;
        int page = 0;
        int pageSize = 10;
        String orden = "";

        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerFiltered(page, pageSize, peliculaId, usuariId, fechaInicio, fechaFin, precio, orden, foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(9)
    @DisplayName("Mostramos los alquileres por fecha")
    public void MostramosAlquileresPorFecha() {

        Optional<Token> foundToken = tokenRepository.findByUsername("admin");

        UUID peliculaId = null;
        UUID usuariId = null;
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = null;
        Integer precio = null;
        int page = 0;
        int pageSize = 10;
        String orden = "";

        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerFiltered(page, pageSize, peliculaId, usuariId, fechaInicio, fechaFin, precio, orden, foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(10)
    @DisplayName("Mostramos los alquileres por precio")
    public void MostramosAlquileresPorPrecio() {

        Optional<Token> foundToken = tokenRepository.findByUsername("admin");

        UUID peliculaId = null;
        UUID usuariId = null;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        Integer precio = 10;
        int page = 0;
        int pageSize = 10;
        String orden = "";

        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerFiltered(page, pageSize, peliculaId, usuariId, fechaInicio, fechaFin, precio, orden, foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(11)
    @DisplayName("Mostramos los alquileres de un usuario")
    public void MostramosAlquileresUsuario() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");

        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerByUser(foundUsuari.get().getId(), foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(12)
    @DisplayName("Mostramos los alquileres de un usuario: token no valido")
    public void MostramosAlquileresUsuarioTokenNoValido() {
        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");

        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerByUser(foundUsuari.get().getId(), "123");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @Order(13)
    @DisplayName("Mostramos un alquiler por su Id")
    public void MostramosAlquilerPorId() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        List<Alquiler> alquileres = alquilerRepository.findByUsuari(foundUsuari.get().getId());
        Optional<Alquiler> foundAlquileres = alquileres.stream().findFirst();
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerById(foundAlquileres.get().getId(), foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(14)
    @DisplayName("Mostramos un alquiler por su Id: token no valido")
    public void MostramosAlquilerPorIdTokenNoValido() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        List<Alquiler> alquileres = alquilerRepository.findByUsuari(foundUsuari.get().getId());
        Optional<Alquiler> foundAlquileres = alquileres.stream().findFirst();
        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerById(foundAlquileres.get().getId(), "123");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }

    @Test
    @Order(15)
    @DisplayName("Mostramos un alquiler por su id: id no valida")
    public void MostramosAlquilerPorIdIdNoValido() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        List<Alquiler> alquileres = alquilerRepository.findByUsuari(foundUsuari.get().getId());
        Optional<Alquiler> foundAlquileres = alquileres.stream().findFirst();
        ResponseEntity<ResponseDTO> response = alquilerService.findAlquilerById(UUID.randomUUID(), "123");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @Order(16)
    @DisplayName("updateem l'estat de l'alquiler")
    public void updateEstatAlquiler() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        List<Alquiler> alquileres = alquilerRepository.findByUsuari(foundUsuari.get().getId());
        Optional<Alquiler> foundAlquileres = alquileres.stream().findFirst();
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = alquilerService.updateAlquilerEstado(EstadoAlquiler.FINALIZADO, foundAlquileres.get().getId(), foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(17)
    @DisplayName("updateem l'estat de l'alquiler: token no valido")
    public void updateEstatAlquilerTokenNoValido() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        List<Alquiler> alquileres = alquilerRepository.findByUsuari(foundUsuari.get().getId());
        Optional<Alquiler> foundAlquileres = alquileres.stream().findFirst();
        ResponseEntity<ResponseDTO> response = alquilerService.updateAlquilerEstado(EstadoAlquiler.FINALIZADO, foundAlquileres.get().getId(), "123");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @Order(18)
    @DisplayName("Esborrem un alquiler")
    public void deleteAlquiler() {

        Optional<Usuari> foundUsuari = usuariRepository.findUserByUsername("demo");
        List<Alquiler> alquileres = alquilerRepository.findByUsuari(foundUsuari.get().getId());
        Optional<Alquiler> foundAlquileres = alquileres.stream().findFirst();
        Optional<Token> foundToken = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = alquilerService.deleteAlquiler(foundAlquileres.get().getId(), foundToken.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
