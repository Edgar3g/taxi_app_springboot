package com.example.demo.Service;

import com.example.demo.Exception.AdminException;
import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.CustomerException;
import com.example.demo.Model.CurrentUserSession;
import com.example.demo.Model.UserLoginDTO;

public interface UserLoginService {

public CurrentUserSession login(UserLoginDTO dto)throws CustomerException, AdminException;
	
	public String LogOut(String uuid)throws CurrentUserSessionException; 
	
	
}
