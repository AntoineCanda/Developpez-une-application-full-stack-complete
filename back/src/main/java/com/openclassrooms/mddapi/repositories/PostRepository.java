package com.openclassrooms.mddapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subject;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findBySubject(Subject subject);
}
