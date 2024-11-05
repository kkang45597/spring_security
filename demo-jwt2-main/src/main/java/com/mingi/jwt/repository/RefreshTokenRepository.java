package com.mingi.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingi.jwt.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
