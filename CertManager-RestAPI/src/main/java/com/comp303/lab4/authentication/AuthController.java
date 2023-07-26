package com.comp303.lab4.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comp303.lab4.candidate.Candidate;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

    	// User Perspective
	@PostMapping("/register")
	public Mono<Candidate> register(@Valid @RequestBody final Candidate candidate) {
		return authService.register(candidate);
	}

	@PostMapping("/login")
	public Mono<LoginResponse> login(@RequestBody final LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		return authService.login(email, password);
	}
	
	@PostMapping("/checkemail")
	public Mono<Boolean> checkEmail(@RequestBody final LoginRequest loginRequest) {
		return authService.isEmailUnique(loginRequest.getEmail());
	}
}
