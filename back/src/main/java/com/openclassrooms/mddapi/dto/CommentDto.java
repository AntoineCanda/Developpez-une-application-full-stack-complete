package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long authorId;
    private Long postId;
    private Timestamp createdDate;
    private String content;
}
