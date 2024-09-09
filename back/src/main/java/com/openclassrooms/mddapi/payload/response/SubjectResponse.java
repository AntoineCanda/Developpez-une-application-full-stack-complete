package com.openclassrooms.mddapi.payload.response;

import java.util.List;

import com.openclassrooms.mddapi.models.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SubjectResponse {

    List<Subject> subjects;
}
