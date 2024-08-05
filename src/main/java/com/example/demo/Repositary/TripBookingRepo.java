package com.example.demo.Repositary;

import com.example.demo.Model.TripBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TripBookingRepo extends JpaRepository<TripBooking, Integer>{

}
