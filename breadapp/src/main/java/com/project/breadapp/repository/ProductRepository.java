package com.project.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.breadapp.models.Product;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	//Product findByEmail(String email);
}
