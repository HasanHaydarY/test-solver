package com.bechallenge.testsolver.student;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
class StudentControllerIT {

    private static final String URL = "/students";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void deleteAll() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "students");
    }

    @Test
    void should_CreateStudent() throws Exception {

        StudentCreateRequest request = StudentData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.firstName").value("Name"))
               .andExpect(jsonPath("$.lastName").value("Surname"));
    }

    @Test
    void should_ThrowException_When_CreateStudent() throws Exception {

        StudentCreateRequest request = StudentData.createRequest();
        request.setFirstName(null);

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void should_FindStudentById() throws Exception {

        StudentCreateRequest request = StudentData.createRequest();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        Long createdStudentId = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), StudentResponse.class).getId();

        mockMvc.perform(get(URL + "/{id}", createdStudentId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value(request.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(request.getLastName()));
    }

    @Test
    void should_GetAllStudents() throws Exception {

        StudentCreateRequest request = StudentData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andDo(mvcResult -> mockMvc.perform(get(URL))
                                          .andExpect(status().isOk())
                                          .andExpect(jsonPath("$.length()").value(1)));
    }

    @Test
    void should_UpdateStudentById() throws Exception {

        StudentCreateRequest request = StudentData.createRequest();
        StudentUpdateRequest updateRequest = StudentData.createUpdateRequest();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        Long createdStudentId = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), StudentResponse.class).getId();

        mockMvc.perform(patch(URL + "/{id}", createdStudentId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value("Name"))
               .andExpect(jsonPath("$.lastName").value("Surname"))
               .andExpect(jsonPath("$.studentNumber").value(111L));
    }

    @Test
    void should_DeleteStudent() throws Exception {

        StudentCreateRequest request = StudentData.createRequest();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        Long createdStudentId = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), StudentResponse.class).getId();

        mockMvc.perform(delete(URL + "/{id}", createdStudentId))
               .andExpect(status().isNoContent());
    }
}
