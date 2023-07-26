package com.comp303.lab4.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CandidateService {

	@Autowired
	private CandidateRepository candidateRepo;

	public Flux<Candidate> getAll() {
		return candidateRepo.findAll();
	}

	public Mono<Candidate> getById(final String _id) {
		return candidateRepo.findById(_id);
	}

	public Mono<Candidate> save(final Candidate candidate) {
		return candidateRepo.save(candidate);
	}

	public Mono<Candidate> update(final String _id, final Candidate candidate) {
		return getById(_id).flatMap(existingCandidate -> {
			// candidate with the given _id exists, update and save it
			existingCandidate.setEmail(candidate.getEmail());
			existingCandidate.setPassword(candidate.getPassword());
			existingCandidate.setFirstName(candidate.getFirstName());
			existingCandidate.setLastName(candidate.getLastName());
			existingCandidate.setStreet(candidate.getStreet());
			existingCandidate.setCity(candidate.getCity());
			existingCandidate.setProvince(candidate.getProvince());
			existingCandidate.setPhone(candidate.getPhone());
			return candidateRepo.save(existingCandidate);
		}).switchIfEmpty(Mono.empty());
	}

	public Mono<Candidate> delete(final String _id) {
		return getById(_id).flatMap(candidateToBeDeleted -> candidateRepo.delete(candidateToBeDeleted)
				.then(Mono.just(candidateToBeDeleted))).switchIfEmpty(Mono.empty());
	}
}
