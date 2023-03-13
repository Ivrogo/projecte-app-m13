package com.movies4rent.Servidor.Repository;

import com.movies4rent.Servidor.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findByUsername(String username);
    Optional<Token> findByToken(String token);
}
