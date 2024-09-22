package com.bechallenge.testsolver.studenttestparticipation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bechallenge.testsolver.student.StudentData;
import com.bechallenge.testsolver.student.StudentResponse;
import com.bechallenge.testsolver.student.StudentService;
import com.bechallenge.testsolver.test.TestData;
import com.bechallenge.testsolver.test.TestResponse;
import com.bechallenge.testsolver.test.TestService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentTestParticipationServiceTest {

    @InjectMocks
    StudentTestParticipationService service;

    @Mock
    StudentService studentService;

    @Mock
    TestService testService;

    @Mock
    StudentTestParticipationRepository repository;

    @Spy
    StudentTestParticipationMapper testParticipationMapper = Mappers.getMapper(StudentTestParticipationMapper.class);

    @Captor
    ArgumentCaptor<StudentTestParticipationEntity> argumentCaptor;

    @Test
    void should_ParticipateInTest() {

        StudentTestParticipationCreateRequest request = StudentTestParticipationData.createRequest();
        StudentTestParticipationEntity entity = StudentTestParticipationData.entity();
        StudentResponse studentResponse = StudentData.response();
        TestResponse testResponse = TestData.response();

        when(repository.findByStudentIdAndTestId(1L, 1L)).thenReturn(null);
        when(studentService.findStudentById(1L)).thenReturn(studentResponse);
        when(testService.findTestById(1L)).thenReturn(testResponse);
        when(repository.save(any())).thenReturn(entity);

        StudentTestParticipationResponse response = assertDoesNotThrow(() -> service.participateInTest(request));

        verify(repository).save(any(StudentTestParticipationEntity.class));
        assertThat(response).isNotNull();
    }

    @Test
    void should_ThrowException_IfFailToFindStudentOrTest_When_ParticipateInTest() {

        StudentTestParticipationCreateRequest request = StudentTestParticipationData.createRequest();
        when(studentService.findStudentById(1L)).thenReturn(null);
        when(testService.findTestById(1L)).thenReturn(null);

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> service.participateInTest(request));

        assertThat(exception.getMessage())
            .isEqualTo("Student with ID " + request.getStudentId() + " or Test with ID " + request.getTestId() + " not found.");
    }

    @Test
    void should_FindStudentTestParticipationById() {

        StudentTestParticipationEntity entity = StudentTestParticipationData.entity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        StudentTestParticipationResponse response = service.findStudentTestParticipationById(1L);

        assertNotNull(response);
        verify(repository).findById(1L);
    }

    @Test
    void should_IfFailToFindStudentTestParticipation_ThrowException_When_FindStudentTestParticipationById() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
            assertThrows(EntityNotFoundException.class, () -> service.findStudentTestParticipationById(1L));

        assertThat(exception.getMessage()).isEqualTo("Student Test Participation with ID " + 1L + " not found");
    }

    @Test
    void should_GetAllStudentTestParticipation() {

        List<StudentTestParticipationEntity> entityList = List.of(StudentTestParticipationData.entity());
        when(repository.findAllByStudentId(1L)).thenReturn(entityList);

        List<StudentTestParticipationResponse> responses = service.getAllStudentTestParticipation(1L);

        assertFalse(responses.isEmpty());
        verify(repository).findAllByStudentId(1L);
    }

    @Test
    void should_ThrowException_IfFailToFindAnyStudentTestParticipation_When_GetAllStudentTestParticipation() {

        when(repository.findAllByStudentId(1L)).thenReturn(List.of());

        EntityNotFoundException exception =
            assertThrows(EntityNotFoundException.class, () -> service.getAllStudentTestParticipation(1L));

        assertThat(exception.getMessage()).isEqualTo("No student test participation found in the database.");
    }
}
