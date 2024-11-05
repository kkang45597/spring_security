package com.mingi.ch6_1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingi.ch6_1.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findUserByUsername(String u);
}
