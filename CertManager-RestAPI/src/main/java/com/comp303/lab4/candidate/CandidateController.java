package com.comp303.lab4.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/candidate")
public class CandidateController {
	
	@Autowired
	private CandidateService candidateService;
	
	@GetMapping
	public Flux<Candidate> getAll() {
		return candidateService.getAll();
	}
	
	@GetMapping("/{_id}")
	public Mono<Candidate> getById(@PathVariable final String _id) {
		return candidateService.getById(_id);
	}
	
	@PostMapping
	public Mono<Candidate> save(@Valid @RequestBody final Candidate candidate) {
		return candidateService.save(candidate);
	}

	@PutMapping("/{_id}")
	public Mono<Candidate> update(@PathVariable final String _id, @Valid @RequestBody final Candidate candidate) {
		return candidateService.update( _id,  candidate);
	}
	
	@DeleteMapping("/{_id}")
	public Mono<Candidate> delete(@PathVariable final String _id) {
		return candidateService.delete(_id);
	}	
}