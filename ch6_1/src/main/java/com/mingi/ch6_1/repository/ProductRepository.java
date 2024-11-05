package com.mingi.ch6_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingi.ch6_1.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
