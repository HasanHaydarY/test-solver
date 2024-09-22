package com.bechallenge.testsolver.test;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResponse {

    private Long id;

    private String testName;

    private List<QuestionDTO> questionDTOList;
}
