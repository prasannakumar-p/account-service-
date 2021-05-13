package com.osg.accountservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.osg.accountservice.entity.User;
import com.osg.accountservice.repository.UserRepository;

@Service
public class UserService {
	private static final String REQUEST_MOBILE = "http://localhost:3002/greeting/";;
	@Autowired
	private UserRepository userRepository;

	public String createUser(User userRequest) {
		String response = "";
		User userReponse = userRepository.findUserByUsername(userRequest.getUsername());

		if (userReponse == null) {
			User user = new User();
			user.setUsername(userRequest.getUsername());
			user.setPassword(userRequest.getPassword());
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			userRepository.save(user);
			response = "save";
		} else {
			response = "error";
		}

		return response;
	}

	public String signIn(User user) {
		String response = "";
		User userResponse = userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
		if (userResponse != null) {
			response = userResponse.getToken();
		} else {
			response = "error";
		}
		return response;
	}

	public User findByToken(String token) {

		return userRepository.findUserByToken(token);
	}

	public String findByTokenAndId(String token, String id) {
		
		RestTemplate restTemplate = new RestTemplate();

		String response = "";
		try {
		
			String responseFromURL = restTemplate.getForObject(REQUEST_MOBILE+id, String.class);
			
			if (!responseFromURL.isEmpty() || !responseFromURL.isBlank()) {
				response = "success";
			} else {
				response = "fails";
			}

		} catch (Exception e) {
			response = e.toString();
		}

		return response;
	}

}
