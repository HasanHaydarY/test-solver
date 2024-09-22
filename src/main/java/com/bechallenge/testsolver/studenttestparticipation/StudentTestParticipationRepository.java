package com.bechallenge.testsolver.studenttestparticipation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentTestParticipationRepository extends JpaRepository<StudentTestParticipationEntity, Long> {

    List<StudentTestParticipationEntity> findAllByStudentId(Long studentId);

    StudentTestParticipationEntity findByStudentIdAndTestId(Long studentId, Long testId);

    List<StudentTestParticipationEntity> findAllByTestId(Long testId);
}
