package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.LoginTokenDTO;
import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.Entities.Token;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe que implementa els metodes utilitzats en el controller, realitzen les funcions login ,logout i register
 * @author Ivan Rodriguez Gomez
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UsuariRepository usuariRepository;
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Implementa el metode login, aquest rep un usuari i una contrasenya i retorna un token de sessió.
     * Dins d'aquest metode es realitzen uns petits controls per evitar que persones que no esten registrades puguin accedir
     * entre altres.
     *
     * @param username username del usuari
     * @param password password del usuari
     * @return un missatge de confirmació de login i un token de sessió
     */
    @Override
    public ResponseEntity<ResponseDTO> login(String username, String password) {

        ResponseDTO<LoginTokenDTO> responseDTO = new ResponseDTO();
        try {
            Optional<Usuari> usuari = usuariRepository.findUserByUsername(username);

            if (!usuari.isPresent()) {
                responseDTO.setMessage("No existe el usuario");
                return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
            } else {
                boolean match = password.equals(usuari.get().getPassword());
                if (!match) {
                    responseDTO.setMessage("La contraseña no coincide");
                    return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
                }
            }

            String token = tokenUtils.checkToken(username);

            LoginTokenDTO loginTokenDTO = new LoginTokenDTO(token, usuari.get().isIsAdmin());
            responseDTO.setMessage("Login correcto");
            responseDTO.setValue(loginTokenDTO);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            responseDTO.setMessage("Error, demo");
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Implementa el metode logout, aquest rep un token i retorna un dto amb un missatge confirmant el fi de la sessió.
     * Dins d'aquest metode es realitzen uns petits controls com per exemple, que el token no siguí vàlid o que la sessió no existi.
     * Basicament aquest metode el que fa ès, si el token ès vàlid, elimina el token de la base de dades.
     *
     * @param token token de sessió de l'usuari.
     * @return un dto amb un missatge confirmant el fi de la sessió.
     */
    @Override
    public ResponseEntity<ResponseDTO> logout(String token) {
        ResponseDTO response = new ResponseDTO();
        try {
            Optional<Token> tokenEntity = tokenRepository.findByToken(token);

            if (!tokenUtils.isTokenValid(tokenEntity.get().getToken())) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            tokenRepository.delete(tokenEntity.get());
            response.setMessage("Sesión cerrada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            response.setMessage("Sesión no existente");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Implementa el metode register, aquest rep un DTO amb la informació basica de l'usuari.
     * Dins d'aquest metode es realitzen uns petits controls com per exemple, que els camps obligatoris com username, contrasenya i email
     * no siguin ni null ni estiguin buits. Finalment aquest metode inserta el nou usuari dins de la base de dades.
     *
     * @param userDTO DTO amb la informació de l'usuari
     * @return DTO amb un missatge confirmant que l'usuari ha estat registrat correctament
     */
    @Override
    public ResponseEntity<ResponseDTO> registerUsuari(RegisterUserDTO userDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            if (userDTO.getPassword().isEmpty() || userDTO.getUsername().isEmpty() || userDTO.getEmail().isEmpty()) {
                response.setMessage("Los campos no pueden estar vacíos");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else if (userDTO.getPassword() == null || userDTO.getUsername() == null || userDTO.getEmail() == null) {
                response.setMessage("Los campos no pueden ser nulos");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            usuariRepository.save(RegisterUserDTO.fromDTOToEntity(userDTO));
            response.setMessage("Usuario registrado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Usuari ja existeix");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }
}
