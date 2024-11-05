package com.mingi.ch6_1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingi.ch6_1.entity.Product;
import com.mingi.ch6_1.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository producteRpository;
	
	public List<Product> findAll() {
		return producteRpository.findAll();
	}
}
