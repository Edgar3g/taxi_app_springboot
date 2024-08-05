package com.example.demo.Service;

import java.util.List;

import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.DriverException;
import com.example.demo.Model.Driver;

public interface DriverService {

    Driver insertDriver(Driver driver)throws DriverException;
	
	Driver updateDriver(Driver driver,String uuid)throws DriverException,CurrentUserSessionException;
	
	Driver deleteDriver(Integer driverId, String uuid)throws DriverException,CurrentUserSessionException;
	
	List<Driver> viewBestDriver(String uuid)throws DriverException,CurrentUserSessionException;
	
	Driver viewDriver(Integer driverId,String uuid)throws DriverException,CurrentUserSessionException;
	
}
