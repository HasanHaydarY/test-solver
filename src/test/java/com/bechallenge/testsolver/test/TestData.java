package com.bechallenge.testsolver.test;

import java.util.Collections;

public class TestData {

    public static final String DEFAULT_NAME = "Name";

    public static QuestionDTO questionDTO() {

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion("test");
        questionDTO.setAnswer(true);

        return questionDTO;
    }

    public static TestEntity entity() {

        TestEntity entity = new TestEntity();
        entity.setTestName(DEFAULT_NAME);
        entity.setQuestionDTOList(Collections.nCopies(10, questionDTO()));
        entity.setId(0L);

        return entity;
    }

    public static TestCreateRequest createRequest() {

        TestCreateRequest request = new TestCreateRequest();
        request.setTestName(DEFAULT_NAME);
        request.setQuestionDTOList(Collections.nCopies(10, questionDTO()));

        return request;
    }

    public static TestUpdateRequest createUpdateRequest() {

        TestUpdateRequest request = new TestUpdateRequest();
        request.setTestName("test");

        return request;
    }

    public static TestResponse response() {

        TestResponse response = new TestResponse();
        response.setTestName(DEFAULT_NAME);
        response.setQuestionDTOList(Collections.nCopies(10, questionDTO()));

        return response;
    }
}
