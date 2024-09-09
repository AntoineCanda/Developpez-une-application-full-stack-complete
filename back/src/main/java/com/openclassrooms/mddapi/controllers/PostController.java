package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            PostDto postDto = postMapper.toDto(post);
            return ResponseEntity.ok().body(postDto);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPostsForUser(@RequestHeader("Authorization") String header) {

        List<Post> posts = postService.getAllPostsForUser(header);
        List<PostDto> postDtos = postMapper.toDto(posts);
        return ResponseEntity.ok().body(postDtos);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        postService.save(post);
        return ResponseEntity.ok(new MessageResponse("Post created!"));
    }
}
