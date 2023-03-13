package com.movies4rent.Servidor.Service;

import com.movies4rent.Servidor.DTO.RegisterUserDTO;
import com.movies4rent.Servidor.DTO.ResponseDTO;
import com.movies4rent.Servidor.DTO.LoginTokenDTO;
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

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UsuariRepository usuariRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public ResponseEntity<ResponseDTO> login(String username, String password) throws Exception {

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
                    return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
                }
            }

            String token = tokenUtils.checkToken(username);

            LoginTokenDTO loginTokenDTO = new LoginTokenDTO(token, usuari.get().isAdmin());
            responseDTO.setValue(loginTokenDTO);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            responseDTO.setMessage("Error, demo");
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> logout(String token) {
        ResponseDTO response = new ResponseDTO();
        try {
            Optional<Token> tokenEntity = tokenRepository.findByToken(token);

            if (!tokenEntity.isPresent()) {
                response.setMessage("Sesión no existente");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            tokenRepository.delete(tokenEntity.get());
            return new ResponseEntity<>(new ResponseDTO(), HttpStatus.OK);

        } catch (Exception ex) {
            response.setMessage("Sesión no existente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> registerUsuari(RegisterUserDTO userDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            usuariRepository.save(RegisterUserDTO.fromDTOToEntity(userDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
