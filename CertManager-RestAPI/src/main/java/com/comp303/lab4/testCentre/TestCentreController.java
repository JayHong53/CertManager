package com.comp303.lab4.testCentre;

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
@RequestMapping("/api/testcentre")
public class TestCentreController {

    @Autowired
    private TestCentreService testCentreService;

    @GetMapping
    public Flux<TestCentre> getAll() {
        return testCentreService.getAll();
    }

    @GetMapping("/{_id}")
    public Mono<TestCentre> getById(@PathVariable final String _id) {
        return testCentreService.getById(_id);
    }

    @PostMapping
    public Mono<TestCentre> save(@RequestBody @Valid final TestCentre testCentre) {
        return testCentreService.save(testCentre);
    }

    @PutMapping("/{_id}")
    public Mono<TestCentre> update(@PathVariable final String _id, @RequestBody @Valid final TestCentre testCentre) {
        return testCentreService.update( _id,  testCentre);
    }

    @DeleteMapping("/{_id}")
    public Mono<TestCentre> delete(@PathVariable final String _id) {
        return testCentreService.delete(_id);
    }
}
