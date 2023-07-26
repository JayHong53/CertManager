package com.comp303.lab4.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.comp303.lab4.candidate.Candidate;
import com.comp303.lab4.candidate.CandidateRepository;
import com.comp303.lab4.certification.Certification;
import com.comp303.lab4.certification.CertificationRepository;
import com.comp303.lab4.testCentre.TestCentre;
import com.comp303.lab4.testCentre.TestCentreRepository;

@Service
@AllArgsConstructor
public class TestService {

	@Autowired
	private TestRepository testRepo;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private CertificationRepository certificationRepository;
	@Autowired
	private TestCentreRepository testCentreRepository;

	// Basic CRUD operations
	public Flux<Test> getAll() {
		// Get Candidate, Certification, and TestCentre objects from the MongoDB
		// database and populate the Test object
		return testRepo.findAll().flatMap(test -> {
			Mono<Candidate> candidateMono = candidateRepository.findById(test.getCandidate().get_id());
			Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id());
			Mono<TestCentre> testCentreMono = testCentreRepository.findById(test.getTestCentre().get_id());

			return Mono.zip(candidateMono, certificationMono, testCentreMono).flatMap(tuple -> {
				test.setCandidate(tuple.getT1());
				test.setCertification(tuple.getT2());
				test.setTestCentre(tuple.getT3());
				return Mono.just(test);
			});
		});
	}

	public Mono<Test> getById(final String _id) {
		// Get Candidate, Certification, and TestCentre objects from the MongoDB
		// database and populate the Test object
		return testRepo.findById(_id).flatMap(test -> {
			Mono<Candidate> candidateMono = candidateRepository.findById(test.getCandidate().get_id());
			Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id());
			Mono<TestCentre> testCentreMono = testCentreRepository.findById(test.getTestCentre().get_id());

			return Mono.zip(candidateMono, certificationMono, testCentreMono).flatMap(tuple -> {
				test.setCandidate(tuple.getT1());
				test.setCertification(tuple.getT2());
				test.setTestCentre(tuple.getT3());
				return Mono.just(test);
			});
		}).switchIfEmpty(Mono.error(new Exception("Test data could not be retrieved")));
	}

	// *** Save method will be used for testing purpose only ****
	// *** For booking, use bookTest method below ****
	public Mono<Test> save(final Test test) {
		// Validate if Candidate, Certification, and TestCentre exist in the MongoDB
		// database
		Mono<Candidate> candidateMono = candidateRepository.findById(test.getCandidate().get_id());
		Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id());
		Mono<TestCentre> testCentreMono = testCentreRepository.findById(test.getTestCentre().get_id());

		return Mono.zip(candidateMono, certificationMono, testCentreMono).flatMap(tuple -> {
			test.setCandidate(tuple.getT1());
			test.setCertification(tuple.getT2());
			test.setTestCentre(tuple.getT3());
			return testRepo.save(test);
		}).switchIfEmpty(Mono.error(new Exception("Test data could not be saved")));
	}

	public Mono<Test> update(final String _id, final Test test) {

		// Validate if Candidate, Certification, and TestCentre exist in the MongoDB
		// database
		Mono<Candidate> candidateMono = candidateRepository.findById(test.getCandidate().get_id());
		Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id());
		Mono<TestCentre> testCentreMono = testCentreRepository.findById(test.getTestCentre().get_id());

		return getById(_id).flatMap(existingTest -> {
			existingTest.setCandidate(test.getCandidate());
			existingTest.setCertification(test.getCertification());
			existingTest.setTestCentre(test.getTestCentre());
			existingTest.setTestDateTime(test.getTestDateTime());
			existingTest.setScore(test.getScore());
			existingTest.setResult(test.getResult());
			existingTest.setStatus(test.getStatus());

			return Mono.zip(candidateMono, certificationMono, testCentreMono).flatMap(tuple -> {
				existingTest.setCandidate(tuple.getT1());
				existingTest.setCertification(tuple.getT2());
				existingTest.setTestCentre(tuple.getT3());
				return testRepo.save(existingTest);
			});
		}).switchIfEmpty(Mono.error(new Exception("Test with id " + _id + " could not be updated")));
	}

	public Mono<Test> delete(final String _id) {
		return getById(_id)
				.flatMap(testToBeDeleted -> testRepo.delete(testToBeDeleted).then(Mono.just(testToBeDeleted)))
				.switchIfEmpty(Mono.error(new Exception("Test with id " + _id + " could not be deleted")));
	} // Basic CRUD end

	// ***********************************
	// By Admin functions
	// ***********************************
	// Mark a test 
	public Mono<Test> markTest(final String _id, final double score) {
	    return testRepo.findById(_id)
	            .flatMap(test -> {
	                // Get the Certification associated with the Test
	                Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id());

	                // Update the Test result based on the score and certification's passing grade
	                return certificationMono.flatMap(certification -> {
	                    if (score >= certification.getPassingScore()) {
	                        test.setResult("Pass");
	                    } else {
	                        test.setResult("Fail");
	                    }
	                    test.setStatus("Completed");
	                    test.setScore(score);

	                    // Save the updated Test in the database
	                    return testRepo.save(test);
	                });
	            })
	            .switchIfEmpty(Mono.error(new Exception("Score for test id " + _id + " could not be saved")));
	}
	
	// ***********************************
	// By Candidate functions
	// ***********************************

	// Get All Tests By Candidate
	public Flux<Test> getAllTestByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id)
				.flatMapMany(candidateObj -> testRepo.findByCandidate(candidateObj));
	}

	// Get Tests By Candidate - Active Test ( Test Date == Future )
	public Flux<Test> getActiveTestsByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id).flatMapMany(candidateObj -> testRepo.findByCandidate(candidateObj))
				.filter(test -> test.getTestDateTime().isAfter(LocalDateTime.now())
						&& test.getStatus().equals("Scheduled"));
	}

	// Get Tests By Candidate - Test Eligible for reschedule ( Test Date > 1 week
	// from now )
	public Flux<Test> getTestsEligibleForRescheduleByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id).flatMapMany(candidateObj -> {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime oneWeekLater = now.plusWeeks(1);
			return testRepo.findByCandidate(candidateObj)
					.filter(test -> test.getTestDateTime().isAfter(oneWeekLater)
							&& test.getStatus().equals("Scheduled"));
		});
	}
	
	// Canceled tests 
	public Flux<Test> getCancelledTestsByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id).flatMapMany(candidateObj -> {
			return testRepo.findByCandidate(candidateObj).filter(test -> test.getStatus().equals("Cancelled"));
		});
	}

	// Get Tests By Candidate - Past Tests ( Test Date == Past )
	public Flux<Test> getPastTestsByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id).flatMapMany(candidateObj -> testRepo.findByCandidate(candidateObj))
				.filter(test -> test.getTestDateTime().isBefore(LocalDateTime.now()));
	}

	// Get Tests By Candidate - Passed Tests ( Test Result == Pass )
	public Flux<Test> getPassedTestsByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id).flatMapMany(candidateObj -> testRepo.findByCandidate(candidateObj))
				.filter(test -> test.getResult().equals("Pass"));
	}

	// Get Tests By Candidate - Failed Tests ( Test Result == Fail )
	public Flux<Test> getFailedTestsByCandidate(final String user_Id) {
		return candidateRepository.findById(user_Id).flatMapMany(candidateObj -> testRepo.findByCandidate(candidateObj))
				.filter(test -> test.getResult().equals("Fail"));
	}
	
	// book a Test
	public Mono<Test> bookTest(final Test test) {
		// Validate if Candidate, Certification, and TestCentre exist in the MongoDB
		// Database
		Mono<Candidate> candidateMono = candidateRepository.findById(test.getCandidate().get_id())
				.switchIfEmpty(Mono.error(new Exception("Candidate data not found")));
		Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id())
				.switchIfEmpty(Mono.error(new Exception("Certification data not found")));
		Mono<TestCentre> testCentreMono = testCentreRepository.findById(test.getTestCentre().get_id())
				.switchIfEmpty(Mono.error(new Exception("TestCentre data not found")));
		
		// Get duration of the certification and set the end time 
		int testDuration = certificationMono.map(certification -> certification.getDuration()).block();
		LocalDateTime testEndDateTime = test.getTestDateTime().plusMinutes(testDuration);
		
		// Check for Schedule Conflict
		if (hasScheduleConflict(test.getTestDateTime(), testEndDateTime, test.getCandidate().get_id()) ) {
			return Mono.error(new Exception("You have a conflict schedule"));
		} 
		
		return Mono.zip(candidateMono, certificationMono, testCentreMono).flatMap(tuple -> {
			test.setCandidate(tuple.getT1());
			test.setCertification(tuple.getT2());
			test.setTestCentre(tuple.getT3());
			test.setTestEndDateTime(testEndDateTime);
			test.setStatus("Scheduled");
			test.setResult("N/A");
			return testRepo.save(test);
		}).switchIfEmpty(Mono.error(new Exception("Test data could not be saved")));
	}
	
	// Reschedule Test
	public Mono<Test> rescheduleTest(final String user_id, final String test_id, final Test test) {
		
		// Reschedule Test
		// If candidate is not the owner of the test, throw a Mono Error
		if (!user_id.equals(test.getCandidate().get_id())) {
			return Mono.error(new Exception("You are not authorized to change the schedule"));
		}
		
		// If the test is not reschedule-eligible, throw a Mono error
		if (!isRescheduleEligible(test_id)) {
			return Mono.error(new Exception("This test is not eligible for rescheduling"));
		}

		// If the time selected is not the future, throw a Mono Error
		if (!test.getTestDateTime().isAfter(LocalDateTime.now())) {
			return Mono.error(new Exception("Test date time should be in the future"));
		}

		// Else, reschedule the test.
		Mono<Candidate> candidateMono = candidateRepository.findById(test.getCandidate().get_id())
				.switchIfEmpty(Mono.error(new Exception("Candidate data not found")));
		Mono<Certification> certificationMono = certificationRepository.findById(test.getCertification().get_id())
				.switchIfEmpty(Mono.error(new Exception("Certification data not found")));
		Mono<TestCentre> testCentreMono = testCentreRepository.findById(test.getTestCentre().get_id())
				.switchIfEmpty(Mono.error(new Exception("TestCentre data not found")));
		
		// Get duration of the certification and set the end time 
		int testDuration = certificationMono.map(certification -> certification.getDuration()).block();
		LocalDateTime testEndDateTime = test.getTestDateTime().plusMinutes(testDuration);
		
		// Check for Schedule Conflict
		if (hasScheduleConflict(test.getTestDateTime(), testEndDateTime, test.getCandidate().get_id()) ) {
			return Mono.error(new Exception("You have a conflict schedule"));
		} 

		return getById(test_id).flatMap(existingTest -> {
			existingTest.setCandidate(test.getCandidate());
			existingTest.setCertification(test.getCertification());
			existingTest.setTestCentre(test.getTestCentre());
			existingTest.setTestDateTime(test.getTestDateTime());

			return Mono.zip(candidateMono, certificationMono, testCentreMono).flatMap(tuple -> {
				existingTest.setCandidate(tuple.getT1());
				existingTest.setCertification(tuple.getT2());
				existingTest.setTestCentre(tuple.getT3());
				existingTest.setTestEndDateTime(testEndDateTime);
				return testRepo.save(existingTest);
			});
		}).switchIfEmpty(
				Mono.error(new Exception("Reschedule process with id " + test_id + " could not be completed")));
	}

	// Cancel Test
	public Mono<Test> cancelTest(final String user_id, final String test_id) {
		return getById(test_id).flatMap(test -> {
			// if the candidate is not the owner, throw an error
			if (!test.getCandidate().get_id().equals(user_id)) {
				return Mono.error(new Exception("You are not authorized to cancel this test"));
			}
			// if the test is already completed, throw an error
			if (test.getTestDateTime().isBefore(LocalDateTime.now())) {
				return Mono.error(new Exception("Completed tests cannot be cancelled"));
			}
			test.setStatus("Cancelled");
			return testRepo.save(test).then(Mono.just(test));
		}).switchIfEmpty(Mono.error(new Exception("Test with id " + test_id + " could not be canceled")));
	}
	
	// Method for checking duplicate schedule 
	private boolean hasScheduleConflict(LocalDateTime start, LocalDateTime end, String user_id) {
		return candidateRepository.findById(user_id).flatMapMany(candidateObj -> testRepo.findConflictSchedule(start, end, candidateObj))
		.hasElements().block();		
	}
	
	private boolean isRescheduleEligible(String test_id) {
	    return testRepo.findById(test_id)
	            .map(test -> {
	                LocalDateTime now = LocalDateTime.now();
	                LocalDateTime oneWeekLater = now.plusWeeks(1);
	                return test.getTestDateTime().isAfter(oneWeekLater);
	            })
	            .blockOptional() // Block and get the optional Test object
	            .orElse(false); // Return false if Test object is not present
	}
}