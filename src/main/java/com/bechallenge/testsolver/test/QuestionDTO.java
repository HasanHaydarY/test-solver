package com.bechallenge.testsolver.test;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class QuestionDTO {

    private String question;

    private Boolean answer;
}
