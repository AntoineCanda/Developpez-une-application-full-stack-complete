package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;

import com.openclassrooms.mddapi.dto.SubjectDto;
import com.openclassrooms.mddapi.models.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<SubjectDto, Subject> {

}
