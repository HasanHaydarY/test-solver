package com.bechallenge.testsolver.student;

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
class StudentServiceTest {

    @InjectMocks
    StudentService service;

    @Mock
    StudentRepository repository;

    @Captor
    ArgumentCaptor<StudentEntity> studentEntityArgumentCaptor;

    @Spy
    StudentMapper mapper = Mappers.getMapper(StudentMapper.class);

    @Test
    void should_CreateStudent() {

        StudentCreateRequest request = StudentData.createRequest();

        assertDoesNotThrow(() -> service.createStudent(request));

        verify(repository).save(studentEntityArgumentCaptor.capture());

        StudentEntity entity = studentEntityArgumentCaptor.getValue();
        assertThat(request.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(request.getLastName()).isEqualTo(entity.getLastName());
        assertThat(request.getStudentNumber()).isEqualTo(entity.getStudentNumber());
    }

    @Test
    void should_FindStudentEntityById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(StudentData.entity()));

        StudentEntity entity = assertDoesNotThrow(() -> service.findStudentEntityById(anyLong()));

        verify(repository).findById(anyLong());
        assertThat(entity).isNotNull();
    }

    @Test
    void should_ThrowException_When_FindStudentEntityById() {

        when(repository.findById(0L)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.findStudentEntityById(0L));

        verify(repository).findById(0L);
    }

    @Test
    void should_FindStudentById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(StudentData.entity()));

        StudentResponse response = assertDoesNotThrow(() -> service.findStudentById(anyLong()));

        verify(repository).findById(anyLong());
        assertThat(response).isNotNull();
    }

    @Test
    void should_GetAllStudents() {

        when(repository.findAll()).thenReturn(Collections.singletonList(StudentData.entity()));

        List<StudentResponse> responseList = assertDoesNotThrow(() -> service.getAllStudents());

        verify(repository).findAll();
        assertThat(responseList).isNotEmpty();
    }

    @Test
    void should_ThrowException_When_GetAllStudents() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        EntityNotFoundException exception =
            assertThrows(EntityNotFoundException.class, () -> service.getAllStudents());

        verify(repository).findAll();
        assertThat(exception.getMessage()).isEqualTo("No students found in the database.");
    }

    @Test
    void should_UpdateStudentById() {

        StudentUpdateRequest request = StudentData.createUpdateRequest();

        when(repository.findById(anyLong())).thenReturn(Optional.of(StudentData.entity()));

        assertDoesNotThrow(() -> service.updateStudentById(request, anyLong()));

        verify(repository).save(studentEntityArgumentCaptor.capture());

        StudentEntity entity = studentEntityArgumentCaptor.getValue();
        assertThat(request.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(request.getLastName()).isEqualTo(entity.getLastName());
        assertThat(request.getStudentNumber()).isEqualTo(entity.getStudentNumber());
    }

    @Test
    void should_DeleteStudent() {

        StudentEntity entity = StudentData.entity();
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> service.deleteStudentById(entity.getId()));

        Mockito.verify(repository).delete(entity);
    }
}
