package com.openclassrooms.mddapi.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.utils.PostSortDescByCreatedDateComparator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final PostRepository postRepository;

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> getAllPostsForUser(String header) {
        String token = jwtUtils.parseTokenFromAuthorizationHeader(header);
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User user = userService.findByEmail(email);
        List<Subject> subjects = user.getSubjects();

        List<Post> allPostsBySubject = new ArrayList<>();
        for (Subject subject : subjects) {
            Optional<List<Post>> posts = postRepository.findAllBySubjectOrderByCreatedDateDesc(subject);
            if (posts.isPresent()) {
                allPostsBySubject.addAll(posts.get());
            }
        }
        Collections.sort(allPostsBySubject, new PostSortDescByCreatedDateComparator());

        return allPostsBySubject;
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Post findById(Long id) {
        return this.postRepository.findById(id).orElse(null);
    }
}
