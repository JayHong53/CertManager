package com.comp303.lab4.certification;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CertificationRepository extends ReactiveMongoRepository<Certification, String>{
    
}
