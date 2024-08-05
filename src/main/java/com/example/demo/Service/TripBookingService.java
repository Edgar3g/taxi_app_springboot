package com.example.demo.Service;

import java.util.List;

import com.example.demo.Exception.CabException;
import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.TripBookingException;
import com.example.demo.Model.Cab;
import com.example.demo.Model.TripBooking;
import com.example.demo.Model.TripBookingDTO;

public interface TripBookingService {

	List<Cab> searchByLocation(String pickUpLocation,String uuid)throws TripBookingException,CurrentUserSessionException;
	
	TripBooking BookRequest(Integer cabId,TripBooking tripBooking,String uuid) throws TripBookingException,CabException,CurrentUserSessionException;
	
	TripBooking AssignDriverByAdmin(Integer TripBookingId,String uuid)throws TripBookingException,CabException,CurrentUserSessionException;
	
	TripBookingDTO viewBookingById(Integer TripBookingId,String uuid )throws TripBookingException,CabException,CurrentUserSessionException;
	
	String MarkTripAsCompleted(Integer TripBookingId,String uuid)throws TripBookingException,CurrentUserSessionException;;
}
