package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.SubjectDto;
import com.openclassrooms.mddapi.mapper.SubjectMapper;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.payload.response.SubjectResponse;
import com.openclassrooms.mddapi.services.SubjectService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subject")
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @GetMapping("/")
    public ResponseEntity<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        SubjectResponse response = new SubjectResponse(subjects);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<MessageResponse> createSubject(@RequestBody SubjectDto dto) {
        Subject subject = subjectMapper.toEntity(dto);
        subjectService.create(subject);

        return ResponseEntity.ok(new MessageResponse("Subject created!"));
    }
}
