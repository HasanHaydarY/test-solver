package com.bechallenge.testsolver.studenttestparticipation;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "studentTestParticipation")
@Getter
@Setter
@SequenceGenerator(name = "default-sequence-generator", sequenceName = "registered_device_id_seq")
public class StudentTestParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "studentId")
    private Long studentId;

    @Column(name = "testId")
    private Long testId;

    @Column(name = "score")
    private Double score;

    @Column(name = "isActive")
    private Boolean isActive = true;

    @Column(name = "answerList")
    @ElementCollection
    private List<Boolean> answerList;
}
