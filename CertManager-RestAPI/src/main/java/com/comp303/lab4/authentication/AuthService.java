package com.comp303.lab4.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comp303.lab4.candidate.Candidate;
import com.comp303.lab4.candidate.CandidateRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthService {
	
	@Autowired
	private CandidateRepository candidateRepo;

	// User Perspective
	public Mono<Candidate> register(final Candidate candidate) {
		try {
			Mono<Candidate> existingCandidate = candidateRepo.findByEmail(candidate.getEmail());
			if (existingCandidate.block() != null) {
				return Mono.error(new Exception("User already exists"));
			} else {
				return candidateRepo.save(candidate);
			}
		} catch (Exception e) {
			return Mono.error(new Exception("Registration process could not be completed"));
		}
	}

	public Mono<LoginResponse> login(final String email, final String password) {
		try {
			Mono<Candidate> candidate = candidateRepo.findByEmail(email);
			if (candidate.block() == null) {
				return Mono.error(new Exception("User not found"));
			} else {
				if (candidate.block().getPassword().equals(password)) {
					return Mono.just(new LoginResponse(candidate.block().get_id(), candidate.block().getEmail(), candidate.block().getFirstName(), candidate.block().getLastName()));
				} else {
					return Mono.error(new Exception("Invalid password"));
				}
			}
		} catch (Exception e) {
			return Mono.error(new Exception("Login process could not be completed"));
		}
	}
	
	public Mono<Boolean> isEmailUnique(final String email) {
	    try {
	        Mono<Candidate> existingCandidate = candidateRepo.findByEmail(email);
	        return existingCandidate.map(candidate -> false) 
	                .defaultIfEmpty(true); 
	    } catch (Exception e) {
	        return Mono.error(new Exception("Process could not be completed"));
	    }
	}
}
