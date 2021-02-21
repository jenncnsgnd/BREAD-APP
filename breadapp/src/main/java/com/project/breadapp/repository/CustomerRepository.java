package com.project.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.breadapp.models.Customer;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	//User findByEmail(String email);
}
