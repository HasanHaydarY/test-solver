package com.bechallenge.testsolver.studenttestparticipation;

import com.bechallenge.testsolver.student.StudentResponse;
import com.bechallenge.testsolver.student.StudentService;
import com.bechallenge.testsolver.test.QuestionDTO;
import com.bechallenge.testsolver.test.TestResponse;
import com.bechallenge.testsolver.test.TestService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentTestParticipationService {

    private final StudentService studentService;

    private final TestService testService;

    private final StudentTestParticipationRepository repository;

    private final StudentTestParticipationMapper participationMapper;

    /**
     * Handles a student's participation in a test by saving their answers and calculating their score.
     *
     * @param request the request containing student ID, test ID, and the student's answers.
     * @return the response object containing participation details.
     * @throws IllegalArgumentException if the student or test is not found.
     */
    public StudentTestParticipationResponse participateInTest(final StudentTestParticipationCreateRequest request) throws IllegalArgumentException {

        StudentResponse studentResponse = studentService.findStudentById(request.getStudentId());
        TestResponse testResponse = testService.findTestById(request.getTestId());

        if (studentResponse == null || testResponse == null) {
            throw new IllegalArgumentException(
                "Student with ID " + request.getStudentId() + " or Test with ID " + request.getTestId() + " not found."
            );
        }

        List<Boolean> correctAnswers = testResponse.getQuestionDTOList().stream().map(QuestionDTO::getAnswer).toList();
        StudentTestParticipationEntity entity = saveStudentTestParticipation(request, calculateScore(correctAnswers, request.getAnswerList()));
        return participationMapper.toResponse(entity);
    }

    /**
     * Checks if there is an active test participation record for the given student and test.
     *
     * @param studentId the student's ID.
     * @param testId the test's ID.
     * @return true if there is an active participation, false otherwise.
     */
    private boolean isActiveStudentTestParticipation(final Long studentId, final Long testId) {
        Optional<StudentTestParticipationEntity> optionalEntity = Optional.ofNullable(
            repository.findByStudentIdAndTestId(studentId, testId)
        );
        return optionalEntity.map(StudentTestParticipationEntity::getIsActive).orElse(false);
    }

    /**
     * Finds a student's test participation by its ID.
     *
     * @param id the participation ID.
     * @return the participation entity.
     * @throws EntityNotFoundException if no participation is found for the given ID.
     */
    public StudentTestParticipationEntity findStudentTestParticipationEntityById(final Long id) throws EntityNotFoundException {

        return repository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException("Student Test Participation with ID " + id + " not found"));
    }

    /**
     * Retrieves all test participations for a given student.
     *
     * @param studentId the student's ID.
     * @return a list of student test participation responses.
     * @throws EntityNotFoundException if no participation records are found for the student.
     */
    public List<StudentTestParticipationResponse> getAllStudentTestParticipation(final Long studentId) {

        List<StudentTestParticipationEntity> entityList = repository.findAllByStudentId(studentId);

        if (entityList.isEmpty()) {
            throw new EntityNotFoundException("No student test participation found in the database.");
        }

        return entityList.stream().map(participationMapper::toResponse).toList();
    }

    /**
     * Calculates the score of a student based on their answers and the correct answers.
     *
     * @param correctAnswers the correct answers for the test.
     * @param studentAnswers the student's answers for the test.
     * @return the calculated score as a percentage.
     */
    private double calculateScore(final List<Boolean> correctAnswers, final List<Boolean> studentAnswers) {

        int score = 0;
        for (int i = 0; i < studentAnswers.size(); i++) {
            if (correctAnswers.get(i).equals(studentAnswers.get(i))) {
                score++;
            }
        }
        return (score / (double) correctAnswers.size()) * 100;
    }

    /**
     * Saves or updates a student's test participation record, marking it inactive if the maximum number of answers is reached.
     *
     * @param request the request containing the student's answers.
     * @param score the student's score for the test.
     * @return the saved participation entity.
     */
    private StudentTestParticipationEntity saveStudentTestParticipation(final StudentTestParticipationCreateRequest request, final double score) {

        StudentTestParticipationEntity entity;

        if (isActiveStudentTestParticipation(request.getStudentId(), request.getTestId())) {
            entity = repository.findByStudentIdAndTestId(request.getStudentId(), request.getTestId());
        } else {
            entity = new StudentTestParticipationEntity();
        }
        if (request.getAnswerList().size() == 10) {
            entity.setIsActive(false);
        }
        participationMapper.toEntity(request, score, entity);
        repository.save(entity);

        return entity;
    }

    /**
     * Finds a student's test participation by its ID and returns a response object.
     *
     * @param id the participation ID.
     * @return the participation response.
     */
    public StudentTestParticipationResponse findStudentTestParticipationById(final Long id) {

        StudentTestParticipationEntity entity = findStudentTestParticipationEntityById(id);
        return participationMapper.toResponse(entity);
    }

    /**
     * Retrieves all test participations for a given test.
     *
     * @param testId the test's ID.
     * @return a list of student test participation responses.
     * @throws EntityNotFoundException if no participation records are found for the test.
     */
    public List<StudentTestParticipationResponse> getAllStudentTestParticipationByTestId(final Long testId) {

        List<StudentTestParticipationEntity> entityList = repository.findAllByTestId(testId);

        if (entityList.isEmpty()) {
            throw new EntityNotFoundException("No student test participation found in the database.");
        }

        return entityList.stream().map(participationMapper::toResponse).toList();
    }
}
