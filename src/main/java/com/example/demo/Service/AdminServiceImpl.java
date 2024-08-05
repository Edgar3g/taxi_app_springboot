package com.example.demo.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.AdminException;
import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.CustomerException;
import com.example.demo.Exception.TripBookingException;
import com.example.demo.Model.Admin;
import com.example.demo.Model.CurrentUserSession;
import com.example.demo.Model.Customer;
import com.example.demo.Model.TripBooking;
import com.example.demo.Repositary.AdminRepo;
import com.example.demo.Repositary.CurrentUserSessionRepo;
import com.example.demo.Repositary.CustomerRepo;
import com.example.demo.Repositary.TripBookingRepo;


@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private TripBookingRepo tripbookingRepo;
		
	@Autowired
	private CurrentUserSessionRepo currRepo;
	
	@Override
	public Admin insertAdmin(Admin admin) throws AdminException {
		
		Optional<Admin> adn = adminRepo.findByEmail(admin.getEmail());
		if(adn.isPresent()) {
			throw new AdminException("Admin is Already Registered");
		}
		else {
			if(admin.getUserRole().equalsIgnoreCase("Admin")) {
				return adminRepo.save(admin);
			}
			else {
				throw new AdminException("The User is not an Admin");
			}
		}
	}


	@Override
	public Admin updateAdmin(Admin admin, String uuid) throws AdminException, CurrentUserSessionException {
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuidAndRole(uuid);
		if(validCustomer.isPresent()) {
			Optional<Admin> adn = adminRepo.findByEmail(admin.getEmail());
			if(adn.isPresent()) {
				Admin forUpdate = adn.get();
				forUpdate.setAddress(admin.getAddress());
				forUpdate.setMobileNumber(admin.getMobileNumber());
				forUpdate.setPassword(admin.getPassword());
				forUpdate.setUserName(admin.getUserName());
				adminRepo.save(forUpdate);
				return forUpdate;
			}
			else {
				throw new AdminException("Admin with this Credential is not present");
			}
		}
		else {
			throw new CurrentUserSessionException("Admin is Not Logged In");
		}
	}


	@Override
	public Admin deleteAdmin(Integer adminId, String uuid) throws AdminException, CurrentUserSessionException {
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuidAndRole(uuid);
		if(validCustomer.isPresent()) {
			Optional<Admin> adn = adminRepo.findById(adminId);
			if(adn.isPresent()) {
				Admin forDelete = adn.get();
				adminRepo.delete(forDelete);
				return forDelete;
			}
			else {
				throw new AdminException("Admin with this Credential is not present");
			}
		}
		else {
			throw new CurrentUserSessionException("Admin is Not Logged In");
		}
	}


	@Override
	public List<TripBooking> getAllTrips(String uuid)
			throws AdminException, TripBookingException, CurrentUserSessionException{
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuidAndRole(uuid);
		if(validCustomer.isPresent()) {
			List<TripBooking> allTrips = tripbookingRepo.findAll();
			if(allTrips.isEmpty()) {
				throw new TripBookingException("No Trip is Booked Currently By any Customer");
			}
			else {
				return allTrips;
			}
		}
		else {
			throw new CurrentUserSessionException("Admin is Not Logged In Or User is not Admin");
		}
	}


	@Override
	public List<TripBooking> getTripsCabwise(String carType, String uuid)
			throws TripBookingException, CurrentUserSessionException {
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuidAndRole(uuid);
		if(validCustomer.isPresent()) {
			List<TripBooking> allTrips = tripbookingRepo.findAll();
			if(allTrips.isEmpty()) {
				throw new TripBookingException("No Trip is Booked Currently By any Customer");
			}
			else {
				List<TripBooking> cabWiseTrips = new ArrayList<>();
				for(TripBooking tb : allTrips) {
					if(tb.getCab().getCarType().equalsIgnoreCase(carType)) {
						cabWiseTrips.add(tb);
					}
				}
				if(cabWiseTrips.isEmpty()) {
					throw new TripBookingException("No Trip Found With this carType");
				}
				else {
					return cabWiseTrips;
				}
			}
		}
		else {
			throw new CurrentUserSessionException("Admin is Not Logged In");
		}
	}


	@Override
	public List<TripBooking> getTripsCustomerwise(Integer customerId, String uuid)
			throws TripBookingException,CustomerException, CurrentUserSessionException {
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuidAndRole(uuid);
		if(validCustomer.isPresent()) {
			Optional<Customer> cust = customerRepo.findById(customerId);
			if(cust.isPresent()) {
				Customer customer = cust.get();
				List<TripBooking> customerTrips = customer.getTripBooking(); 
				if(customerTrips.isEmpty()) {
					throw new CustomerException("No Trip Bookked by the customer");
				}
				else {
					return customerTrips;
				}
			}
			else {
				throw new CustomerException("Customer with this Credential is not present");
			}
		}
		else {
			throw new CurrentUserSessionException("Admin is Not Logged In Or User is not Admin");
		}
	}


	@Override
	public List<TripBooking> getAllTripsForDays(Integer customerId, String fromDateTime, String toDateTime,
			String uuid) throws TripBookingException,CustomerException, CurrentUserSessionException {
		
		Optional<CurrentUserSession> validCustomer = currRepo.findByUuidAndRole(uuid);
		if(validCustomer.isPresent()) {
	        Optional<Customer> cust = customerRepo.findById(customerId);
	        if(cust.isPresent()) {
	        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	            LocalDateTime fromDT = LocalDateTime.parse(fromDateTime, formatter);
	            LocalDateTime toDT = LocalDateTime.parse(toDateTime, formatter);
	        	Customer customer = cust.get();
	        	List<TripBooking> customerTrips = customer.getTripBooking();
	        	for(TripBooking tb : customerTrips) {
	        		LocalDateTime currentTripfromDT = LocalDateTime.parse(tb.getFromDateTime(), formatter);
		            LocalDateTime currentTriptoDT = LocalDateTime.parse(tb.getToDateTime(), formatter);
	        		if(currentTripfromDT.isAfter(fromDT) && currentTriptoDT.isBefore(toDT)) {
	        			customerTrips.add(tb);
	        		}
	        	}
	        	
	        	if(customerTrips.isEmpty()) {
	        		throw new TripBookingException("No Trip has been booked in between of the given Dates");
	        	}
	        	else {
	        		return customerTrips;
	        	}
	        }
	        else {
	        	throw new CustomerException("No Customer Found with this Credentials");
	        }
		}
		else {
			throw new CurrentUserSessionException("Admin is Not Logged In");
		}
	}

	
}
