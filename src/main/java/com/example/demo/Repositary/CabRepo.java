package com.example.demo.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Cab;

public interface CabRepo extends JpaRepository<Cab, Integer>{

	Optional<Cab> findByCarNumber(String carNumber);
}
