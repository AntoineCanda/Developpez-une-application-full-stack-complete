package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.mddapi.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Timestamp createdDate;
    private Long authorId;
    private Long subjectId;

    @JsonIgnore
    private List<Comment> comments;
}
