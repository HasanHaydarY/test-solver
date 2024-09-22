package com.bechallenge.testsolver.test;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
class TestController {

    private final TestService service;

    @PostMapping
    public ResponseEntity<TestResponse> createTest(@Valid @RequestBody final TestCreateRequest request) {

        return ResponseEntity.status(201).body(service.createTest(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> findTestById(@PathVariable final Long id) {

        return ResponseEntity.ok(service.findTestById(id));
    }

    @GetMapping
    public ResponseEntity<List<TestResponse>> getAllTests() {

        return ResponseEntity.ok(service.getAllTests());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TestResponse> updateTestById(@Valid @RequestBody final TestUpdateRequest request, @PathVariable final Long id) {

        return ResponseEntity.ok(service.updateTestById(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestById(@PathVariable final Long id) {

        service.deleteTestById(id);
        return ResponseEntity.noContent().build();
    }
}
