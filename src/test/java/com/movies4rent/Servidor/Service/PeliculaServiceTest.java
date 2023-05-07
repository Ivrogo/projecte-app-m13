package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.PeliculaUpdateDTO;
import com.movies4rent.Servidor.DTO.RegisterPeliculaDTO;
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

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeliculaServiceTest {

    @Autowired
    private UsuariService usuariService;

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
    @DisplayName("Añade una pelicula")
    public void AfegeixPeliculaOk() {

        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias");
        registerPeliculaDTO.setDirector("George Lucas");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(6)
    @DisplayName("Añade una pelicula sin titulo")
    public void AfegeixPeliculaSinTitulo() {
        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setDirector("George Lucas1");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Añade una pelicula sin director")
    public void AfegeixPeliculaSinDirector() {
        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias2");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(8)
    @DisplayName("Añade una pelicula sin genero")
    public void AfegeixPeliculaSinGenero() {
        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias3");
        registerPeliculaDTO.setDirector("George Lucas");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(9)
    @DisplayName("Añade una pelicula: no tiene permisos")
    public void AfegeixPeliculaSinPermisos() {

        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias");
        registerPeliculaDTO.setDirector("George Lucas");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }

    @Test
    @Order(10)
    @DisplayName("Añade una pelicula: titulo vacio")
    public void afegeixPeliculaTituloEmpty() {

        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("");
        registerPeliculaDTO.setDirector("George Lucas");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(11)
    @DisplayName("Añade una pelicula: director vacio")
    public void afegeixPeliculaDirectorEmpty() {
        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias");
        registerPeliculaDTO.setDirector("");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    @Order(12)
    @DisplayName("Añade una pelicula: genero vacio")
    public void afegeixPeliculaGeneroEmpty() {
        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias");
        registerPeliculaDTO.setDirector("George Lucas");
        registerPeliculaDTO.setGenero("");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(13)
    @DisplayName("Añade una pelicula: la pelicula ya existe")
    public void afegeixPeliculaYaExiste() {
        RegisterPeliculaDTO registerPeliculaDTO = new RegisterPeliculaDTO();
        registerPeliculaDTO.setTitulo("La guerra de las galaxias");
        registerPeliculaDTO.setDirector("George Lucas");
        registerPeliculaDTO.setGenero("Ciencia ficción");
        registerPeliculaDTO.setDuracion(190);
        registerPeliculaDTO.setAño(2020);
        registerPeliculaDTO.setPrecio(12);

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.addPelicula(token.get().getToken(), registerPeliculaDTO);
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @Order(14)
    @DisplayName("Muestra todas las peliculas")
    public void MuestraTodasLasPeliculas() {

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("La guerra de las galaxias 2");
        pelicula.setDirector("George Lucas");
        pelicula.setGenero("Ciencia ficción");
        pelicula.setDuracion(190);
        pelicula.setAño(2021);
        pelicula.setPrecio(12);
        pelicula.setVecesAlquilada(4);

        peliculaRepository.save(pelicula);
        int page = 0;
        int pageSize = 5;


        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize, pelicula.getTitulo(), pelicula.getDirector(), pelicula.getGenero(), pelicula.getAño(), pelicula.getVecesAlquilada(), token.get().getToken(), "");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(15)
    @DisplayName("Muestra peliculas segun el director")
    public void MuestraUnaPeliculaFiltroDirector() {

        int page = 0;
        int pageSize = 5;
        Integer año = null;
        Integer vecesAlquilada = null;
        String director = "George Lucas";
        String genero = "";
        String orden = "";
        String titulo = "";

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize,titulo, director, genero, año, vecesAlquilada, token.get().getToken(), orden);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(16)
    @DisplayName("Muestra peliculas segun el genero")
    public void MuestraUnaPeliculaFiltroGenero() {

        int page = 0;
        int pageSize = 5;
        Integer año = null;
        Integer vecesAlquilada = null;
        String titulo = "";
        String director = "";
        String genero = "Ciencia ficción";
        String orden = "";

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize, titulo, director, genero, año, vecesAlquilada, token.get().getToken(), orden);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(17)
    @DisplayName("Muestra peliculas segun el año")
    public void MuestraUnaPeliculaFiltroAño() {

        int page = 0;
        int pageSize = 5;
        Integer año = 2020;
        Integer vecesAlquilada = null;
        String titulo = "";
        String director = "";
        String genero = "";
        String orden = "";

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize, titulo, director, genero, año, vecesAlquilada, token.get().getToken(), orden);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(18)
    @DisplayName("Muestra peliculas segun el vecesAlquilada")
    public void MuestraUnaPeliculaFiltroVecesAlquilada() {

        int page = 0;
        int pageSize = 5;
        Integer año = null;
        Integer vecesAlquilada = 4;
        String titulo = "";
        String director = "";
        String genero = "";
        String orden = "";

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize, titulo, director, genero, año, vecesAlquilada, token.get().getToken(), orden);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(19)
    @DisplayName("Muestra peliculas segun el titulo")
    public void MuestrapeliculasFiltroTitulo() {

        int page = 0;
        int pageSize = 5;
        Integer año = null;
        Integer vecesAlquilada = null;
        String titulo = "La guerra de las galaxias";
        String director = "";
        String genero = "";
        String orden = "";

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize, titulo, director, genero, año, vecesAlquilada, token.get().getToken(), orden);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(20)
    @DisplayName("Muestra peliculas pero token no es valido")
    public void MuestraUnaPeliculaFiltroTokenInvalido() {
        int page = 0;
        int pageSize = 5;
        Integer año = null;
        Integer vecesAlquilada = null;
        String titulo = "";
        String director = "";
        String genero = "";
        String orden = "";

        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findPeliculasFiltered(page, pageSize, titulo, director, genero, año, vecesAlquilada, "", orden);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    @Order(21)
    @DisplayName("Muestra una pelicula")
    public void MuestraUnaPelicula() {

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("La guerra de las galaxias");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.findById(foundPelicula.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(22)
    @DisplayName("updatea una pelicula")
    public void updateaUnaPelicula() {

        PeliculaUpdateDTO peliculaUpdateDTO = new PeliculaUpdateDTO();
        peliculaUpdateDTO.setTitulo("La guerra de las galaxiaslol");
        peliculaUpdateDTO.setDirector("George Lucas");
        peliculaUpdateDTO.setGenero("Ciencia ficción");
        peliculaUpdateDTO.setDuracion(190);
        peliculaUpdateDTO.setAño(2020);

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("La guerra de las galaxias");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.updatePelicula(peliculaUpdateDTO, foundPelicula.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @Order(23)
    @DisplayName("updatea una pelicula: token no valido")
    public void updateaUnaPeliculaTokenInvalido() {

        PeliculaUpdateDTO peliculaUpdateDTO = new PeliculaUpdateDTO();
        peliculaUpdateDTO.setTitulo("La guerra de las galaxias");
        peliculaUpdateDTO.setDirector("George Lucas");
        peliculaUpdateDTO.setGenero("Ciencia ficción");
        peliculaUpdateDTO.setDuracion(190);
        peliculaUpdateDTO.setAño(2020);

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("La guerra de las galaxias 2");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.updatePelicula(peliculaUpdateDTO, foundPelicula.get().getId(), "");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(24)
    @DisplayName("updatea una pelicula: no tiene permisos")
    public void updateaUnaPeliculaNoTienePermisos() {
        PeliculaUpdateDTO peliculaUpdateDTO = new PeliculaUpdateDTO();
        peliculaUpdateDTO.setTitulo("La guerra de las galaxias");
        peliculaUpdateDTO.setDirector("George Lucas");
        peliculaUpdateDTO.setGenero("Ciencia ficción");
        peliculaUpdateDTO.setDuracion(190);
        peliculaUpdateDTO.setAño(2020);

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("La guerra de las galaxiaslol");
        Optional<Token> token = tokenRepository.findByUsername("demo");
        ResponseEntity<ResponseDTO> response = peliculaService.updatePelicula(peliculaUpdateDTO, foundPelicula.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @Order(25)
    @DisplayName("elimina una pelicula")
    public void eliminaUnaPelicula() {

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("La guerra de las galaxiaslol");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.deletePelicula(foundPelicula.get().getId(), token.get().getToken());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(26)
    @DisplayName("elimina una pelicula: token no valido")
    public void eliminaUnaPeliculaTokenInvalido() {

        Optional<Pelicula> foundPelicula = peliculaRepository.findByTitulo("La guerra de las galaxias 2");
        Optional<Token> token = tokenRepository.findByUsername("admin");
        ResponseEntity<ResponseDTO> response = peliculaService.deletePelicula(foundPelicula.get().getId(), "");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
