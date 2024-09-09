package com.openclassrooms.mddapi.mapper.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Post;

@Service
public interface PostMapperService {

    public Post findById(Long id);
}
