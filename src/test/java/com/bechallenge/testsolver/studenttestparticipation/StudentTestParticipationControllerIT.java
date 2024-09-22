package com.bechallenge.testsolver.studenttestparticipation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bechallenge.testsolver.student.StudentCreateRequest;
import com.bechallenge.testsolver.student.StudentData;
import com.bechallenge.testsolver.test.TestCreateRequest;
import com.bechallenge.testsolver.test.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentTestParticipationControllerIT {

    private static final String URL = "/test-participation";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void deleteData() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "student_test_participation_entity_answer_list");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "student_test_participation");
    }

    @AfterAll
    public void deleteAll() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "test_entity_questiondtolist");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tests");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "students");
    }

    @BeforeAll
    public void createData() throws Exception {



        StudentCreateRequest studentRequest = StudentData.createRequest();
        mockMvc.perform(post("/students")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(studentRequest)))
               .andExpect(status().isCreated());

        TestCreateRequest testRequest = TestData.createRequest();
        mockMvc.perform(post("/tests")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testRequest)))
               .andExpect(status().isCreated());
    }


    @Test
    void should_createStudentTestParticipation() throws Exception {

        StudentTestParticipationCreateRequest request = StudentTestParticipationData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.testId").value(request.getTestId()))
               .andExpect(jsonPath("$.studentId").value(request.getStudentId()));
    }

    @Test
    void should_findStudentTestParticipationById() throws Exception {

        StudentTestParticipationCreateRequest request = StudentTestParticipationData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andDo(result -> {
                   String responseString = result.getResponse().getContentAsString();
                   StudentTestParticipationResponse response = objectMapper.readValue(responseString, StudentTestParticipationResponse.class);
                   Long createdId = response.getId();

                   mockMvc.perform(get(URL + "/by-id/{id}", createdId))
                          .andExpect(status().isOk())
                          .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(createdId));
               });
    }

    @Test
    void should_findStudentTestParticipationByTestId() throws Exception {

        StudentTestParticipationCreateRequest request = StudentTestParticipationData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andDo(result -> {
                String responseString = result.getResponse().getContentAsString();
                StudentTestParticipationResponse response = objectMapper.readValue(responseString, StudentTestParticipationResponse.class);
                Long testId = response.getTestId();

                mockMvc.perform(get(URL + "/by-test-id/{testId}", testId))
                       .andExpect(status().isOk())
                       .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
               });
    }

    @Test
    void should_findStudentTestParticipationByStudentId() throws Exception {

        StudentTestParticipationCreateRequest request = StudentTestParticipationData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andDo(result -> {
                   String responseString = result.getResponse().getContentAsString();
                   StudentTestParticipationResponse response = objectMapper.readValue(responseString, StudentTestParticipationResponse.class);
                   Long studentId = response.getStudentId();

                    mockMvc.perform(get(URL + "/by-student/{studentId}", studentId))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
               });
    }
}
