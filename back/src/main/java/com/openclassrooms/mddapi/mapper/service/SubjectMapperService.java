package com.openclassrooms.mddapi.mapper.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Subject;

@Service
public interface SubjectMapperService {

    public Subject findById(Long id);
}
