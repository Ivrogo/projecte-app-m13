package com.movies4rent.Servidor.Utils;

import com.movies4rent.Servidor.Entities.Token;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.TokenRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;


/**
 * Classe Utilitat (es casi com un servei, per això està declarat com servei a Spring) que permet validar tokens, obtenir usuaris des de tokens,
 * guardar tokens, entre altres.
 * @author Iván Rodríguez Gómez
 */
@Service
public class TokenUtils {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UsuariRepository usuariRepository;

    /**
     * Metode que checkea el token d'un usuari
     * @param username username del usuari
     * @return retorna el token d'un usuari que s'ha afegit a la base de dades
     */
    public String checkToken(String username) {
        Optional<Token> token = tokenRepository.findByUsername(username);

        if (!token.isPresent()) {
            return saveToken(username);
        } else {
            if (tokenValidity(token.get().getLastUsed())) {
                token.get().setLastUsed(LocalDateTime.now());

                tokenRepository.save(token.get());

                return token.get().getToken();
            } else {
                tokenRepository.delete(token.get());
                return saveToken(username);
            }
        }
    }

    /**
     * Metode que obté un usuari a partir d'un token de sessió
     * @param token token de sessió de l'usuari
     * @return un objecte usuari
     */
    public Optional<Usuari> getUser(String token) {
        Optional<Token> tokenEntity = tokenRepository.findByToken(token);

        if (!tokenEntity.isPresent()) {
            return null;
        } else {
            if (tokenValidity(tokenEntity.get().getLastUsed())) {

                Optional<Usuari> user = usuariRepository.findUserByUsername(tokenEntity.get().getUsername());
                if (!user.isPresent()) {
                    tokenRepository.delete(tokenEntity.get());
                    return null;
                }

                tokenEntity.get().setLastUsed(LocalDateTime.now());
                tokenRepository.save(tokenEntity.get());
                return user;

            } else {
                return null;
            }
        }
    }

    /**
     * Metode que valida el token d'un usuari
     * @param token token de sessió de l'usuari
     * @return True si el token és vàlid, False en cas contrari
     */
    public boolean isTokenValid(String token) {
        Optional<Token> tokenEntity = tokenRepository.findByToken(token);

        if (!tokenEntity.isPresent()) {
            return false;
        } else {
            if (tokenValidity(tokenEntity.get().getLastUsed())) {


                Optional<Usuari> user = usuariRepository.findUserByUsername(tokenEntity.get().getUsername());
                if (!user.isPresent()) {
                    tokenRepository.delete(tokenEntity.get());
                    return false;
                }

                tokenEntity.get().setLastUsed(LocalDateTime.now());
                tokenRepository.save(tokenEntity.get());

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Metode que guarda un token d'un usuari
     * @param username username del usuari
     * @return token d'un usuari en format String
     */
    private String saveToken(String username) {
        String tokenString = getEncodedString();

        Token tokenToSave = new Token();
        tokenToSave.setToken(tokenString);
        tokenToSave.setUsername(username);
        tokenToSave.setCreationDate(LocalDateTime.now());
        tokenToSave.setLastUsed(LocalDateTime.now());

        tokenRepository.save(tokenToSave);

        return tokenString;
    }

    /**
     * Metode que valida el temps d'un token d'un usuari
     * @param lastUsed parametre que indica l'última vegada que s'ha utilitzat el token
     * @return el temps que ha de ser inferior a una hora
     */
    private static boolean tokenValidity(LocalDateTime lastUsed) {
        LocalDateTime dateNow = LocalDateTime.now();
        long time = ChronoUnit.HOURS.between(lastUsed, dateNow);
        return time <= 1;
    }

    /**
     * Metode que obté un token en format String
     * @return retorna el token en format String
     */
    private static String getEncodedString() {
        UUID tokenUUID = UUID.randomUUID();
        String tokenToEncode = tokenUUID.toString().replace("-", "");
        return tokenToEncode;
    }

    /**
     * Metode que Desencripta un token
     * @param tokenToEncode token encriptat
     * @return retorna el token desencriptat
     */
    private static String getDecryptedString(String tokenToEncode) {
        return new String(Base64.getMimeDecoder().decode(tokenToEncode));
    }
}
