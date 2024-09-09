package com.openclassrooms.mddapi.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.mddapi.models.Subject;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {

    private Long id;

    private Timestamp createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    @Size(max = 120)
    private String password;

    @NonNull
    @Size(max = 50)
    private String username;

    @NonNull
    @Size(max = 50)
    @Email
    private String email;

    @JsonIgnore
    private boolean admin;

    private List<Subject> subjects;
}
