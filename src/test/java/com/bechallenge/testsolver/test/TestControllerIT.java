package com.bechallenge.testsolver.test;

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
class TestControllerIT {

    private static final String URL = "/tests";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void deleteAll() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "test_entity_questiondtolist");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tests");
    }

    @Test
    void should_CreateTest() throws Exception {

        TestCreateRequest request = TestData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.testName").value("Name"));
    }

    @Test
    void should_ThrowException_When_CreateTest() throws Exception {

        TestCreateRequest request = TestData.createRequest();
        request.setTestName(null);

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void should_FindTestById() throws Exception {

        TestCreateRequest request = TestData.createRequest();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        Long createdTestId = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponse.class).getId();

        mockMvc.perform(get(URL + "/{id}", createdTestId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.testName").value(request.getTestName()));
    }

    @Test
    void should_GetAllTests() throws Exception {

        TestCreateRequest request = TestData.createRequest();

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andDo(mvcResult -> mockMvc.perform(get(URL))
                                          .andExpect(status().isOk())
                                          .andExpect(jsonPath("$.length()").value(1)));
    }

    @Test
    void should_UpdateTestById() throws Exception {

        TestCreateRequest request = TestData.createRequest();
        TestUpdateRequest updateRequest = TestData.createUpdateRequest();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        Long createdTestId = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponse.class).getId();

        mockMvc.perform(patch(URL + "/{id}", createdTestId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.testName").value("test"));
    }

    @Test
    void should_DeleteTest() throws Exception {

        TestCreateRequest request = TestData.createRequest();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        Long createdTestId = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponse.class).getId();

        mockMvc.perform(delete(URL + "/{id}", createdTestId))
               .andExpect(status().isNoContent());
    }
}
