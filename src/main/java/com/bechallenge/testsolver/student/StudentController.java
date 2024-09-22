package com.bechallenge.testsolver.student;

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
@RequestMapping("/students")
class StudentController {

    private final StudentService service;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody final StudentCreateRequest request) {

        return ResponseEntity.status(201).body(service.createStudent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> findStudentById(@PathVariable final Long id) {

        return ResponseEntity.ok(service.findStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(service.getAllStudents());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudentById(@Valid @RequestBody final StudentUpdateRequest request
        ,@PathVariable final Long id) {

        return ResponseEntity.ok(service.updateStudentById(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable final Long id) {

        service.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
