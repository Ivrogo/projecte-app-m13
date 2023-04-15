package com.movies4rent.Servidor.Controller;


import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.UserChangePasswordDTO;
import com.movies4rent.Servidor.DTO.UserUpdateDTO;
import com.movies4rent.Servidor.Repository.UsuariPagingRepository;
import com.movies4rent.Servidor.Service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Classe que controla la logica sobre els usuaris.
 * @author Ivan Rodriguez Gomez
 */
@RestController
@RequestMapping("/users")
public class UsuariControllerImpl implements UsuariController {

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private UsuariPagingRepository usuariPagingRepository;


    @Override
    @GetMapping
    public ResponseEntity<ResponseDTO> getUsuarisFiltered(int page, int pageSize, String nombre, String apellidos, String username, String orden, String token) {
        return usuariService.findUsuariFiltered(page, pageSize, nombre, apellidos, username, orden, token);
    }

    /**
     * Rep un token de sessió i una id d'usuari i retorna un usuari específic
     * @param id de l'usuari
     * @param token token de sessió
     * @return metode findById de usuariService
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getUsuari(UUID id, String token) {
        return usuariService.findById(id, token);
    }

    /**
     * Rep un DTO amb els canvis de la informació de l'usuari i un token de sessió, i retorna un missatge confirmant els canvis.
     * @param userUpdateDTO DTO amb els canvis de la informació de l'usuari
     * @param token token de sessió
     * @return metode update de usuariService
     */
    @Override
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUsuari(@RequestBody UserUpdateDTO userUpdateDTO, @RequestParam(value = "token", required = true) String token) {
        return usuariService.updateUser(userUpdateDTO, token);
    }

    /**
     * Rep un boolean admin, una id d'usuari i un token de sessió, i retorna un missatge de confirmació amb els canvis.
     * @param admin per saber si es admin o no
     * @param id de l'usuari
     * @param token token de sessió
     * @return metode updateUserAdmin de usuariService
     */
    @PutMapping("/update/{id}/{admin}")
    public ResponseEntity<ResponseDTO> updateUsuariAdmin(@PathVariable("admin") boolean admin, @PathVariable("id") UUID id, @RequestParam(value = "token", required = true) String token) {
        return usuariService.updateUserAdmin(admin, id, token);
    }

    /**
     * Rep un token de sessió i una id d'usuari i retorna un missatge de confirmant que s'ha esborrat l'usuari.
     * @param id de l'usuari
     * @param token token de sessió
     * @return metode deleteUser de usuariService
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUsuari(@PathVariable UUID id, String token) {
        return usuariService.deleteUser(id, token);
    }

    /**
     * Rep un token de sessió i retorna la informació de l'usuari d'aquest token.
     * @param token token de sessió
     * @return metode findByToken de usuariService
     */
    @Override
    @GetMapping("/info")
    public ResponseEntity<ResponseDTO> getInfoUsuariByToken(String token) {
        return usuariService.getUserByToken(token);
    }

    @Override
    @PutMapping("/changepassword")
    public ResponseEntity<ResponseDTO> changeUsuariPassword(String token, UserChangePasswordDTO userChangePasswordDTO) {
        return usuariService.changeUsuariPassword(token, userChangePasswordDTO);
    }

}
