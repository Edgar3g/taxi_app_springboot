package com.example.demo.Repositary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Customer;
import com.example.demo.Model.Driver;

public interface DriverRepo extends JpaRepository<Driver, Integer>{

	Optional<Driver> findByLicenceNo(String licenceNo);
	
	Optional<Driver> findByEmail(String email);

	List<Driver> findByCurrLocationAndCurrDriverStatus(String currLocation, String currDriverStatus);
}
