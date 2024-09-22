package com.bechallenge.testsolver.studenttestparticipation;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentTestParticipationResponse {

    private Long id;

    private Long studentId;

    private Long testId;

    private Double score;

    private Boolean isActive;

    private List<Boolean> answerList;
}
