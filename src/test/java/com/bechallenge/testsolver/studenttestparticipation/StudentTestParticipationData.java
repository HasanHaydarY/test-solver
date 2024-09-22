package com.bechallenge.testsolver.studenttestparticipation;

import java.util.Collections;

public class StudentTestParticipationData {

   public static StudentTestParticipationEntity entity() {

       StudentTestParticipationEntity entity = new StudentTestParticipationEntity();
       entity.setId(1L);
       entity.setStudentId(1L);
       entity.setTestId(1L);
       entity.setScore(100.00);
       entity.setAnswerList(Collections.nCopies(10, true));

       return entity;
   }

    public static StudentTestParticipationCreateRequest createRequest() {

        StudentTestParticipationCreateRequest request = new StudentTestParticipationCreateRequest();
        request.setStudentId(1L);
        request.setTestId(1L);
        request.setAnswerList(Collections.nCopies(10, true));

        return request;
    }
}
