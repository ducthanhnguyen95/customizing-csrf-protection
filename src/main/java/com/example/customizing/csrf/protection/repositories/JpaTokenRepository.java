package com.example.customizing.csrf.protection.repositories;

import com.example.customizing.csrf.protection.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTokenRepository  extends JpaRepository<Token, Integer> {

    Optional<Token> findTokenByIdentifier(String identifier);
}
