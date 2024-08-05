package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.AdminException;
import com.example.demo.Exception.CurrentUserSessionException;
import com.example.demo.Exception.CustomerException;
import com.example.demo.Model.CurrentUserSession;
import com.example.demo.Model.UserLoginDTO;
import com.example.demo.Service.UserLoginService;


@RestController
@RequestMapping("/Userlogin")
public class LoginController {

	@Autowired
	private UserLoginService userlogService;
	
	@PostMapping("/Login")
	public ResponseEntity<CurrentUserSession> loginHandler(@RequestBody UserLoginDTO dto) throws CustomerException, AdminException {
		return new ResponseEntity<CurrentUserSession>(userlogService.login(dto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logoutHandler(@RequestParam String uuid) throws CurrentUserSessionException {
		return new ResponseEntity<String>(userlogService.LogOut(uuid), HttpStatus.OK);
	}
	
}
