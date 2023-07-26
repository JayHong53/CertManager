package com.comp303.lab4.certification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CertificationService {

	@Autowired
	private CertificationRepository certificationRepo;

	public Flux<Certification> getAll() {
		return certificationRepo.findAll();
	}

	public Mono<Certification> getById(final String _id) {
		return certificationRepo.findById(_id);
	}

	public Mono<Certification> save(final Certification certification) {
		return certificationRepo.save(certification);
	}

	public Mono<Certification> update(final String _id, final Certification certification) {
		return getById(_id).flatMap(existingCert -> {
			existingCert.setCertificationCode(certification.getCertificationCode());
			existingCert.setCertificationName(certification.getCertificationName());
			existingCert.setFormat(certification.getFormat());
			existingCert.setDuration(certification.getDuration());
			existingCert.setNumberOfQuestions(certification.getNumberOfQuestions());
			existingCert.setPassingScore(certification.getPassingScore());
			existingCert.setFee(certification.getFee());

			return certificationRepo.save(existingCert);
		}).switchIfEmpty(Mono.error(new Exception("Certification with id " + _id + " could not be updated")));
	}

	public Mono<Certification> delete(final String _id) {
		return getById(_id).flatMap(certificationToBeDeleted -> certificationRepo.delete(certificationToBeDeleted)
				.then(Mono.just(certificationToBeDeleted)))
				.switchIfEmpty(Mono.error(new Exception("Certification with id " + _id + " could not be deleted")));
	}
}
