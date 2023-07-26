package com.comp303.lab4.testCentre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TestCentreService {

    @Autowired
    private TestCentreRepository testCentreRepo;

    public Flux<TestCentre> getAll() {
        return testCentreRepo.findAll();
    }

    public Mono<TestCentre> getById(final String _id) {
        return testCentreRepo.findById(_id);
    }

    public Mono<TestCentre> save(final TestCentre testCentre) {
        return testCentreRepo.save(testCentre);
    }

    public Mono<TestCentre> update(final String _id, final TestCentre testCentre) {
        return getById(_id).flatMap(existingCentre -> {
            existingCentre.setTestCentreCode(testCentre.getTestCentreCode());
            existingCentre.setTestCentreName(testCentre.getTestCentreName());
            existingCentre.setStreet(testCentre.getStreet());
            existingCentre.setCity(testCentre.getCity());
            existingCentre.setProvince(testCentre.getProvince());
            existingCentre.setPhone(testCentre.getPhone());
            existingCentre.setWebsite(testCentre.getWebsite());

            return testCentreRepo.save(existingCentre);
        }).switchIfEmpty(Mono.error(new Exception("Test Centre with id " + _id + " could not be updated")));
    }

	public Mono<TestCentre> delete(final String _id) {
		return getById(_id).flatMap(testCentreToBeDeleted -> testCentreRepo.delete(testCentreToBeDeleted)
				.then(Mono.just(testCentreToBeDeleted)))
				.switchIfEmpty(Mono.error(new Exception("Test Centre with id " + _id + " could not be deleted")));
	}
}
