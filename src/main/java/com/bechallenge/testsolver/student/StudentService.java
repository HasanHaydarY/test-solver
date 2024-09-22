package com.bechallenge.testsolver.student;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    private final StudentMapper studentMapper;

    /**
     * Creates a new Student entity from the given request data.
     *
     * @param request the request containing the details of the student to create.
     * @return the created student's response data.
     */
    public StudentResponse createStudent(final StudentCreateRequest request) {

        StudentEntity entity = studentMapper.toEntity(request);
        repository.save(entity);
        return studentMapper.toResponse(entity);
    }

    /**
     * Finds a StudentEntity by its ID. Throws an EntityNotFoundException if the student is not found.
     *
     * @param id the ID of the student to find.
     * @return the found StudentEntity.
     * @throws EntityNotFoundException if no student with the given ID is found.
     */
    public StudentEntity findStudentEntityById(final Long id) throws EntityNotFoundException {

        return repository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found"));
    }

    /**
     * Finds a student by its ID and returns the corresponding response object.
     *
     * @param id the ID of the student to find.
     * @return the response object of the found student.
     * @throws EntityNotFoundException if no student with the given ID is found.
     */
    public StudentResponse findStudentById(final Long id) throws EntityNotFoundException {

        StudentEntity studentEntity = findStudentEntityById(id);
        return studentMapper.toResponse(studentEntity);
    }

    /**
     * Retrieves all students from the database. Throws an exception if no students are found.
     *
     * @return a list of StudentResponse objects representing all students.
     * @throws EntityNotFoundException if no students are found in the database.
     */
    public List<StudentResponse> getAllStudents() throws EntityNotFoundException {

        List<StudentEntity> entityList = repository.findAll();

        if (entityList.isEmpty()) {
            throw new EntityNotFoundException("No students found in the database.");
        }

        return entityList.stream().map(studentMapper::toResponse).toList();
    }

    /**
     * Updates an existing student's data using the provided request and ID.
     *
     * @param request the request containing the updated student details.
     * @param id the ID of the student to update.
     * @return the updated student's response data.
     */
    public StudentResponse updateStudentById(final StudentUpdateRequest request, final Long id) {

        StudentEntity existingEntity = findStudentEntityById(id);
        studentMapper.toEntity(request, existingEntity);
        repository.save(existingEntity);

        return studentMapper.toResponse(existingEntity);
    }

    /**
     * Deletes a student by its ID.
     *
     * @param id the ID of the student to delete.
     * @throws EntityNotFoundException if no student with the given ID is found.
     */
    public void deleteStudentById(final Long id) {

        StudentEntity existingEntity = findStudentEntityById(id);
        repository.delete(existingEntity);
    }
}
