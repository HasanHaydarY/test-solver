package com.bechallenge.testsolver.studenttestparticipation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentTestParticipationCreateRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long testId;

    @Size(min = 1, max = 10, message = "The answer list must contain between 1 and 10 answers.")
    private List<Boolean> answerList;
}
