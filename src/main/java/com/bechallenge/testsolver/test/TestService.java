package com.bechallenge.testsolver.test;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository repository;

    private final TestMapper testMapper;

    /**
     * Creates a new test based on the provided request.
     *
     * @param request the request containing test details, including a list of questions.
     * @return the response object containing the created test details.
     * @throws IllegalArgumentException if the test does not contain exactly 10 questions.
     */
    public TestResponse createTest(final TestCreateRequest request) {

        if (request.getQuestionDTOList().size() != 10) {
            throw new IllegalArgumentException("A test must contain exactly 10 questions.");
        }

        TestEntity entity = testMapper.toEntity(request);
        repository.save(entity);
        return testMapper.toResponse(entity);
    }

    /**
     * Finds a test entity by its ID.
     *
     * @param id the test ID.
     * @return the test entity.
     * @throws EntityNotFoundException if no test is found for the given ID.
     */
    public TestEntity findTestEntityById(final Long id) throws EntityNotFoundException {

        return repository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("Test with ID " + id + " not found"));
    }

    /**
     * Finds a test by its ID and returns the response object.
     *
     * @param id the test ID.
     * @return the response object containing test details.
     */
    public TestResponse findTestById(final Long id) {

        TestEntity entity = findTestEntityById(id);
        return testMapper.toResponse(entity);
    }

    /**
     * Retrieves all tests from the database.
     *
     * @return a list of response objects containing all test details.
     * @throws EntityNotFoundException if no tests are found in the database.
     */
    public List<TestResponse> getAllTests() {

        List<TestEntity> entityList = repository.findAll();

        if (entityList.isEmpty()) {
            throw new EntityNotFoundException("No tests found in the database.");
        }

        return entityList.stream().map(testMapper::toResponse).toList();
    }

    /**
     * Updates an existing test based on the provided request and ID.
     *
     * @param request the request containing updated test details.
     * @param id the ID of the test to be updated.
     * @return the response object containing the updated test details.
     */
    public TestResponse updateTestById(final TestUpdateRequest request, final Long id) {

        TestEntity existingEntity = findTestEntityById(id);
        testMapper.toEntity(request, existingEntity);
        repository.save(existingEntity);

        return testMapper.toResponse(existingEntity);
    }

    /**
     * Deletes a test by its ID.
     *
     * @param id the ID of the test to be deleted.
     */
    public void deleteTestById(final Long id) {

        TestEntity existingEntity = findTestEntityById(id);
        repository.delete(existingEntity);

    }
}
