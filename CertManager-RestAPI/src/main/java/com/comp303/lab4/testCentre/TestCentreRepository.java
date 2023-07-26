package com.comp303.lab4.testCentre;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TestCentreRepository extends ReactiveMongoRepository<TestCentre, String> {
    
}
