package com.mingi.ch10_2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingi.ch10_2.entities.Token;

import java.util.Optional;

public interface JpaTokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findTokenByIdentifier(String identifier);
}