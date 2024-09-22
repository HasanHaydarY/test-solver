package com.bechallenge.testsolver.studenttestparticipation;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-participation")
class StudentTestParticipationController {

    private final StudentTestParticipationService service;

    @PostMapping
    public ResponseEntity<StudentTestParticipationResponse> createStudentTestParticipation(
        @Valid @RequestBody final StudentTestParticipationCreateRequest request) {

        return ResponseEntity.status(201).body(service.participateInTest(request));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<StudentTestParticipationResponse> findStudentTestParticipationById(@PathVariable final Long id) {

        return ResponseEntity.ok(service.findStudentTestParticipationById(id));
    }

    @GetMapping("/by-test-id/{testId}")
    public ResponseEntity<List<StudentTestParticipationResponse>> findStudentTestParticipationByTestId(@PathVariable final Long testId) {

        return ResponseEntity.ok(service.getAllStudentTestParticipationByTestId(testId));
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<StudentTestParticipationResponse>> findStudentTestParticipationByStudentId(@PathVariable final Long studentId) {

        return ResponseEntity.ok(service.getAllStudentTestParticipation(studentId));
    }
}
