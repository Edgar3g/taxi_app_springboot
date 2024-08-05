package com.example.demo.Repositary;

import java.util.Optional;

import com.example.demo.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	Optional<Customer> findByEmail(String email);
}
