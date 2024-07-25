package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.repositories.SubjectRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public void create(Subject subject) {
        subjectRepository.save(subject);
    }

}
