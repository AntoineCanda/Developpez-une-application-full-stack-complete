package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;

@Mapper(componentModel = "spring", uses = {PostService.class, UserService.class}, imports = {PostService.class, UserService.class})
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Mapping(target = "post", expression = "java(commentDto.getPostId() != null ? postService.findById(commentDto.getPostId()) : null)")
    @Mapping(target = "user", expression = "java(commentDto.getAuthorId() != null ? userService.findById(commentDto.getAuthorId()) : null)")
    @Override
    public abstract Comment toEntity(CommentDto commentDto);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "authorId")
    @Override
    public abstract CommentDto toDto(Comment comment);

}
