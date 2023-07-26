package com.comp303.lab4.candidate;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface CandidateRepository extends ReactiveMongoRepository<Candidate, String> {        
        Mono<Candidate> findByEmail(String email);
}
