package com.comp303.lab4.test;

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

import com.comp303.lab4.candidate.Candidate;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;


    // ********************************************
    // Mapping for basic CRUD 
    // ********************************************
    
    @GetMapping
    public Flux<Test> getAll() {
        return testService.getAll();
    }

    @GetMapping("/{_id}")
    public Mono<Test> getById(@PathVariable final String _id) {
        return testService.getById(_id);
    }

    @PostMapping
    public Mono<Test> save(@RequestBody @Valid final Test test) {
        return testService.save(test);
    }

    @PutMapping("/{_id}")
    public Mono<Test> update(@PathVariable final String _id, @Valid @RequestBody final Test test) {
        return testService.update( _id,  test);
    }

    @DeleteMapping("/{_id}")
    public Mono<Test> delete(@PathVariable final String _id) {
        return testService.delete(_id);
    }
    
    // ********************************************
    // For admin - Set score 
    // ********************************************
    @PostMapping("/{_id}/marktest")
    public Mono<Test> giveMarks(@PathVariable final String _id, @RequestBody double score) {
    	return testService.markTest(_id, score);
    }
     
    // ********************************************
    // By Candidate - Get Methods 
    // ********************************************
    @GetMapping("/{user_id}/all")
    public Flux<Test> getAllTestsByCandidate(@PathVariable String user_id) {
    	return testService.getAllTestByCandidate(user_id);
    }
    
    @GetMapping("/{user_id}/active")
    public Flux<Test> getActiveTestsByCandidate(@PathVariable String user_id) {
    	return testService.getActiveTestsByCandidate(user_id);
    }    
    
    @GetMapping("/{user_id}/reschedule-eligible")
    public Flux<Test> getRescheduleEligibleTestsByCandidate(@PathVariable String user_id) {
    	return testService.getTestsEligibleForRescheduleByCandidate(user_id);
    }    
    
    @GetMapping("/{user_id}/cancelled")
    public Flux<Test> getCancelledTestsByCandidate(@PathVariable String user_id) {
    	return testService.getCancelledTestsByCandidate(user_id);
    }    
        
    // Cancelled 
    @GetMapping("/{user_id}/past")
    public Flux<Test> getPastTestsByCandidate(@PathVariable String user_id) {
    	return testService.getPastTestsByCandidate(user_id);
    }
    
    @GetMapping("/{user_id}/passed")
    public Flux<Test> getPassedTestsByCandidate(@PathVariable String user_id) {
    	return testService.getPassedTestsByCandidate(user_id);
    }
    
    @GetMapping("/{user_id}/failed")
    public Flux<Test> getFailedTestsByCandidate(@PathVariable String user_id) {
    	return testService.getFailedTestsByCandidate(user_id);
    }
    
    // ********************************************
    // By Candidate - Book Test  
    // ********************************************
    @PostMapping("/{user_id}/booktest")
    public Mono<Test> bookTest(@PathVariable String user_id, @RequestBody final Test test) {
    	return testService.bookTest(test);
    }
    
    // ********************************************
    // By Candidate - Reschedule Test  
    // ********************************************    
    @PutMapping("/{user_id}/reschedule/{test_id}")
    
    public Mono<Test> rescheduleTest(@PathVariable final String user_id, @PathVariable final String test_id, @Valid @RequestBody final Test test) {

    	return testService.rescheduleTest(user_id, test_id, test);
    }
    
    // ********************************************
    // By Candidate - Cancel Test 
    // ********************************************
    @PutMapping("/{user_id}/cancel/{test_id}")
    public Mono<Test> cancelTest(@PathVariable final String user_id, @PathVariable final String test_id) {
    	        return testService.cancelTest(user_id, test_id);
    }    
}
