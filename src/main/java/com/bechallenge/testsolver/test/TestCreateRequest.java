package com.bechallenge.testsolver.test;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCreateRequest {

    @NotEmpty
    private String testName;

    @NotEmpty
    private List<QuestionDTO> questionDTOList;
}
