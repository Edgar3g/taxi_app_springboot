package com.example.demo.Service;

import java.util.List;

import com.example.demo.Exception.CabException;
import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Model.Cab;

public interface CabService {

	Cab insertCab(Cab cab)throws CabException;
	
	Cab updateCab(Cab cab,String uuid)throws CabException, CurrentUserSessionException;
	
	Cab deleteCab(Integer cabId,String uuid)  throws CabException, CurrentUserSessionException;
	
	List<Cab> viewCabsOfType(String carType,String uuid)throws CabException, CurrentUserSessionException;
	
	Integer countCabsOfType(String carType,String uuid)throws CabException, CurrentUserSessionException;
	
	
}
