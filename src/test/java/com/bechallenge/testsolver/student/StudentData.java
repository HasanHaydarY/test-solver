package com.bechallenge.testsolver.student;

public class StudentData {

    public static final String DEFAULT_NAME = "Name";
    public static final String DEFAULT_SURNAME = "Surname";

    public static StudentEntity entity() {

        StudentEntity entity = new StudentEntity();
        entity.setFirstName(DEFAULT_NAME);
        entity.setLastName(DEFAULT_SURNAME);
        entity.setStudentNumber(1L);
        entity.setId(0L);

        return entity;
    }

    public static StudentCreateRequest createRequest() {

        StudentCreateRequest request = new StudentCreateRequest();
        request.setFirstName(DEFAULT_NAME);
        request.setLastName(DEFAULT_SURNAME);
        request.setStudentNumber(1L);

        return request;
    }

    public static StudentUpdateRequest createUpdateRequest() {

        StudentUpdateRequest request = new StudentUpdateRequest();
        request.setFirstName(DEFAULT_NAME);
        request.setLastName(DEFAULT_SURNAME);
        request.setStudentNumber(111L);

        return request;
    }

    public static StudentResponse response() {

        StudentResponse response = new StudentResponse();
        response.setFirstName(DEFAULT_NAME);
        response.setLastName(DEFAULT_SURNAME);
        response.setStudentNumber(1L);

        return response;
    }
}
