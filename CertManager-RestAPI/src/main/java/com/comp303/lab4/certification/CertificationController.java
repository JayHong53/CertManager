package com.comp303.lab4.certification;

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
@RequestMapping("/api/certification")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @GetMapping
    public Flux<Certification> getAll() {
        return certificationService.getAll();
    }

    @GetMapping("/{_id}")
    public Mono<Certification> getById(@PathVariable final String _id) {
        return certificationService.getById(_id);
    }

    @PostMapping
    public Mono<Certification> save(@RequestBody @Valid final Certification certification) {
        return certificationService.save(certification);
    }

    @PutMapping("/{_id}")
    public Mono<Certification> update(@PathVariable final String _id, @Valid @RequestBody final Certification certification) {
        return certificationService.update( _id,  certification);
    }

    @DeleteMapping("/{_id}")
    public Mono<Certification> delete(@PathVariable final String _id) {
        return certificationService.delete(_id);
    }
}
