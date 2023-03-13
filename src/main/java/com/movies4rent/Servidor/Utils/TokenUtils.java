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

@Service
public class TokenUtils {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UsuariRepository usuariRepository;

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

    private String saveToken(String username) {
        String tokenString = getEncodedString(username);

        Token tokenToSave = new Token();
        tokenToSave.setToken(tokenString);
        tokenToSave.setUsername(username);
        tokenToSave.setCreationDate(LocalDateTime.now());
        tokenToSave.setLastUsed(LocalDateTime.now());

        tokenRepository.save(tokenToSave);

        return tokenString;
    }

    private static boolean tokenValidity(LocalDateTime lastUsed) {
        LocalDateTime dateNow = LocalDateTime.now();
        long time = ChronoUnit.HOURS.between(lastUsed, dateNow);
        return time <= 1;
    }

    private static String getEncodedString(String username) {
        UUID tokenUUID = UUID.randomUUID();
        String tokenToEncode = tokenUUID + " " + username;
        return Base64.getEncoder().encodeToString(tokenToEncode.getBytes());
    }

    private static String getDecryptedString(String tokenToEncode) {
        return new String(Base64.getMimeDecoder().decode(tokenToEncode));
    }
}
