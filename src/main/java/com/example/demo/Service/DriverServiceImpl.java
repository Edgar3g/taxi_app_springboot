package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.CustomerException;
import com.example.demo.Exception.DriverException;
import com.example.demo.Model.CurrentUserSession;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Driver;
import com.example.demo.Repositary.AdminRepo;
import com.example.demo.Repositary.CurrentUserSessionRepo;
import com.example.demo.Repositary.CustomerRepo;
import com.example.demo.Repositary.DriverRepo;
import com.example.demo.Repositary.TripBookingRepo;

@Service
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverRepo driverRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private TripBookingRepo tripbookingRepo;
		
	@Autowired
	private CurrentUserSessionRepo currRepo;
	
	
	@Override
	public Driver insertDriver(Driver driver) throws DriverException {
		
		Optional<Driver> findDriver = driverRepo.findByLicenceNo(driver.getLicenceNo());
		if(findDriver.isPresent()) {
			throw new DriverException("Driver is already registered");
		}
		else {
			if(driver.getUserRole().equalsIgnoreCase("Driver")) {
				return driverRepo.save(driver);
			}
			else {
				throw new DriverException("User is not a Driver");
			}
		}
	}

	@Override
	public Driver updateDriver(Driver driver, String uuid) throws DriverException, CurrentUserSessionException {
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuid(uuid);
		if(validCustomer.isPresent()) {
			Optional<Driver> drv = driverRepo.findByEmail(driver.getEmail());
			if(drv.isPresent()) {
				Driver updatingdriver = drv.get();
				updatingdriver.setAddress(driver.getAddress());
				updatingdriver.setEmail(driver.getEmail());
				updatingdriver.setMobileNumber(driver.getMobileNumber());
				updatingdriver.setPassword(driver.getPassword());
				updatingdriver.setUserName(driver.getUserName());
				updatingdriver.setLicenceNo(driver.getLicenceNo());
				updatingdriver.setCab(driver.getCab());
				
				 return driverRepo.save(updatingdriver);
			}
			else {
				throw new DriverException("Driver not found with this Credentials");
			}
		}
		else {
			throw new CurrentUserSessionException("User is Not Logged In");
		}
	}

	@Override
	public Driver deleteDriver(Integer driverId, String uuid) throws DriverException, CurrentUserSessionException {
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuid(uuid);
		if(validCustomer.isPresent()) {
			Optional<Driver> drv = driverRepo.findById(driverId);
			if(drv.isPresent()) {
				Driver updatingdriver = drv.get();
				driverRepo.delete(updatingdriver);
				return updatingdriver;
			}
			else {
				throw new DriverException("Driver not found with this Credentials");
			}
		}
		else {
			throw new CurrentUserSessionException("User is Not Logged In");
		}
	}

	@Override
	public List<Driver> viewBestDriver(String uuid) throws DriverException, CurrentUserSessionException {
		// TODO Auto-generated method stub
		List<Driver> allDrivers = driverRepo.findAll();
		List<Driver> bestDriver = new ArrayList<>();
		for(Driver d : allDrivers) {
			if(d.getRating()>=4.5) {
				bestDriver.add(d);
			}
		}
		if(bestDriver.isEmpty()) {
			throw new DriverException("No Best Driver Present");
		}
		else {
			Collections.sort(bestDriver,(a,b)-> Float.compare(b.getRating(), a.getRating()));
			return bestDriver;
		}
		
		
	}

	@Override
	public Driver viewDriver(Integer driverId,String uuid) throws DriverException, CurrentUserSessionException {
		// TODO Auto-generated method stub
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuid(uuid);
		if(validCustomer.isPresent()) {
			Optional<Driver> drv = driverRepo.findById(driverId);
			if(drv.isPresent()) {
				return drv.get();
			}
			else {
				throw new DriverException("Driver not found with this Credentials");
			}
		}
		else {
			throw new CurrentUserSessionException("User is Not Logged In");
		}
	}

	
	
}
