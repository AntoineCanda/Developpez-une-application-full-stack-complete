package com.openclassrooms.mddapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByPost(Post post);
}
