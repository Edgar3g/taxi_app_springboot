package com.example.demo.Service;



import java.util.List;

import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.CustomerException;
import com.example.demo.Model.Customer;

public interface CustomerService {

	Customer insertCustomer(Customer customer) throws CustomerException;
	
	Customer updateCustomer(Customer customer,String uuid) throws CustomerException, CurrentUserSessionException;
	
	Customer deleteCustomer(Integer customerId,String uuid)  throws CustomerException, CurrentUserSessionException;
	
	List<Customer> viewCustomer(String uuid)  throws CustomerException, CurrentUserSessionException;
	
	Customer viewCustomer(Integer customerId, String uuid) throws CustomerException, CurrentUserSessionException;
	

	
	
}
