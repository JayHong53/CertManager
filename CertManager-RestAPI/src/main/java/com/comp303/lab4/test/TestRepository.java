package com.comp303.lab4.test;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.comp303.lab4.candidate.Candidate;

import reactor.core.publisher.Flux;

public interface TestRepository extends ReactiveMongoRepository<Test, String> {

	// Find Test by Candidate 
	Flux<Test> findByCandidate(Candidate candidate);
	
	// Query for getting schedule conflicts for a user (Cancelled tests are not considered)
	@Query("{$and: ["
	        + "{$or:[{testEndDateTime:{$gte:?0}},{endDateTime:{$gte:?0}}]}, "
	        + "{$or:[{testDateTime:{$lte:?1}},{startDateTime:{$lte:?1}}]}, "
	        + "{candidate: ?2}, "
	        + "{$or:[{status:{$ne:'Cancelled'}},{status:{$exists:false}}]}"
	        + "]}")
	Flux<Test> findConflictSchedule(LocalDateTime testDateTime, LocalDateTime testEndDateTime, Candidate candidateId);
}
