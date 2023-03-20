package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Classe Interficie que emmagatzema tots el metodes del repositori JPA referents als tokens. Implementa les cerques específiques
 * que utilitzen la classe TokenUtils.
 * @author Iván Rodríguez Gómez
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findByUsername(String username);
    Optional<Token> findByToken(String token);
}
