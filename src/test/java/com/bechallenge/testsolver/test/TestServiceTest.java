package com.bechallenge.testsolver.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @InjectMocks
    TestService service;

    @Mock
    TestRepository repository;

    @Spy
    TestMapper testMapper = Mappers.getMapper(TestMapper.class);

    @Captor
    ArgumentCaptor<TestEntity> testEntityArgumentCaptor;

    @Test
    void should_CreateTest() {

        TestCreateRequest request = TestData.createRequest();

        assertDoesNotThrow(() -> service.createTest(request));

        verify(repository).save(testEntityArgumentCaptor.capture());

        TestEntity entity = testEntityArgumentCaptor.getValue();
        assertThat(request.getTestName()).isEqualTo(entity.getTestName());
    }

    @Test
    void should_FindTestEntityById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.entity()));

        TestEntity entity = assertDoesNotThrow(() -> service.findTestEntityById(anyLong()));

        verify(repository).findById(anyLong());
        assertThat(entity).isNotNull();
    }

    @Test
    void should_ThrowException_When_FindTestEntityById() {

        when(repository.findById(0L)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.findTestEntityById(0L));

        verify(repository).findById(0L);
    }

    @Test
    void should_FindTestById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.entity()));

        TestResponse response = assertDoesNotThrow(() -> service.findTestById(anyLong()));

        verify(repository).findById(anyLong());
        assertThat(response).isNotNull();
    }

    @Test
    void should_GetAllTests() {

        when(repository.findAll()).thenReturn(Collections.singletonList(TestData.entity()));

        List<TestResponse> responseList = assertDoesNotThrow(() -> service.getAllTests());

        verify(repository).findAll();
        assertThat(responseList).isNotEmpty();
    }

    @Test
    void should_ThrowException_When_GetAllTests() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        EntityNotFoundException exception =
            assertThrows(EntityNotFoundException.class, () -> service.getAllTests());

        verify(repository).findAll();
        assertThat(exception.getMessage()).isEqualTo("No tests found in the database.");
    }

    @Test
    void should_UpdateTestById() {

        TestUpdateRequest request = TestData.createUpdateRequest();

        when(repository.findById(anyLong())).thenReturn(Optional.of(TestData.entity()));

        assertDoesNotThrow(() -> service.updateTestById(request, anyLong()));

        verify(repository).save(testEntityArgumentCaptor.capture());

        TestEntity entity = testEntityArgumentCaptor.getValue();
        assertThat(request.getTestName()).isEqualTo(entity.getTestName());

    }

    @Test
    void should_DeleteTest() {

        TestEntity entity = TestData.entity();
        when(repository.findById(0L)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> service.deleteTestById(entity.getId()));

        Mockito.verify(repository).delete(entity);
    }
}
