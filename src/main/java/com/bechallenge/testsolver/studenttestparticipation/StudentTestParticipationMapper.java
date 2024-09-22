package com.bechallenge.testsolver.studenttestparticipation;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudentTestParticipationMapper {

    StudentTestParticipationMapper INSTANCE = Mappers.getMapper(StudentTestParticipationMapper.class);

    StudentTestParticipationResponse toResponse(StudentTestParticipationCreateRequest request, Double score);

    StudentTestParticipationResponse toResponse(StudentTestParticipationEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(StudentTestParticipationCreateRequest request, Double score, @MappingTarget StudentTestParticipationEntity entity);
}
