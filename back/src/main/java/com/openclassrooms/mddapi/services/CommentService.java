package com.openclassrooms.mddapi.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;

    public List<Comment> getAllCommentsFromPost(Long id) {
        Optional<List<Comment>> comments = commentRepository.findAllByPostIdOrderByCreatedDateAsc(id);
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments.get();
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

}
