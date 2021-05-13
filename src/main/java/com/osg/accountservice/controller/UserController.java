package com.osg.accountservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osg.accountservice.entity.User;
import com.osg.accountservice.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService usersService;

	@PostMapping(value = "/account/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> createUser(@RequestBody User user) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		String createUserResponse = usersService.createUser(user);
		if (createUserResponse.equals("save")) {
			respMap.put("status", "ok");
			respMap.put("message", "Account Created");
		} else {
			respMap.put("status", "error");
			respMap.put("message", "Account Already Exist");
		}

		return respMap;

	}

	@PostMapping(value = "/account/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> signIn(@RequestBody User user) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		String response = usersService.signIn(user);
		if (!response.equals("error")) {
			respMap.put("status", "ok");
			respMap.put("message", "Sign In Successful");
			respMap.put("token", response);
		} else {
			respMap.put("status", "error");
			respMap.put("message", "Username Password Mismatch | Account Doesn't Exist");
		}

		return respMap;

	}

	
	@GetMapping(value = "/account/{token}")
	public HttpEntity<User> findByUserId(@PathVariable(value = "token") String token) {
		User response = usersService.findByToken(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/account/status/")
	public Map<String, Object> findByUserByMobile(@RequestHeader(value = "token") String token,@RequestParam String id) {
		String response = usersService.findByTokenAndId(token,id);
		Map<String, Object> respMap = new HashMap<String, Object>();
		if (response.equals("success")) {
			respMap.put("status", "ok");
			respMap.put("message", "Message Sent");
		} else {
			respMap.put("status", "error");
			respMap.put("message", "Message pending | Message Failed");
		}

		return respMap;
	}

	
}
