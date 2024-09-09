package com.openclassrooms.mddapi.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.services.CommentService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getAllCommentsFromAPost(@PathVariable("id") Long id) {
        List<CommentDto> comments = getAndFormatListOfComments(id);
        return ResponseEntity.ok().body(comments);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNewComment(@RequestBody CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        Post post = comment.getPost();

        commentService.saveComment(comment);

        List<CommentDto> comments = getAndFormatListOfComments(post.getId());
        return ResponseEntity.ok().body(comments);
    }

    private List<CommentDto> getAndFormatListOfComments(Long id) {
        List<Comment> comments = commentService.getAllCommentsFromPost(id);
        List<CommentDto> response = commentMapper.toDto(comments);
        return response;
    }
}
