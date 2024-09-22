package com.bechallenge.testsolver.test;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tests")
@Getter
@Setter
@SequenceGenerator(name = "default-sequence-generator", sequenceName = "registered_device_id_seq")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String testName;

    @ElementCollection
    @Size(min = 10, max = 10, message = "A test must have exactly 10 questions.")
    private List<QuestionDTO> questionDTOList = new ArrayList<>();
}
