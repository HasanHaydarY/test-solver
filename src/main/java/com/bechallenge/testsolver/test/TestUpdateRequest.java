package com.bechallenge.testsolver.test;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestUpdateRequest {

    private String testName;

    private List<QuestionDTO> questionDTOList;
}
