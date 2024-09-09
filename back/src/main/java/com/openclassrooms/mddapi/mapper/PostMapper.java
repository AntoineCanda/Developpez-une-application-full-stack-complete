package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.services.SubjectService;
import com.openclassrooms.mddapi.services.UserService;

@Mapper(componentModel = "spring", uses = {SubjectService.class, UserService.class}, imports = {UserService.class, SubjectService.class})
public abstract class PostMapper implements EntityMapper<PostDto, Post> {

    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;

    @Mapping(expression = "java(postDto.getSubjectId() != null ? subjectService.findById(postDto.getSubjectId()) : null)", target = "subject")
    @Mapping(expression = "java(postDto.getAuthorId() != null ? userService.findById(postDto.getAuthorId()) : null)", target = "author")
    @Override
    public abstract Post toEntity(PostDto postDto);

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "author.id", target = "authorId")
    @Override
    public abstract PostDto toDto(Post post);
}
