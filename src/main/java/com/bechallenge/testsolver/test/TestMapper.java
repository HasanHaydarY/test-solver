package com.bechallenge.testsolver.test;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    TestResponse toResponse(TestEntity entity);

    TestEntity toEntity(TestCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(TestUpdateRequest request, @MappingTarget TestEntity entity);
}
