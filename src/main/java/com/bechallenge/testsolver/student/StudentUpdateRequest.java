package com.bechallenge.testsolver.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest {

    private String firstName;

    private String lastName;

    private Long studentNumber;
}
