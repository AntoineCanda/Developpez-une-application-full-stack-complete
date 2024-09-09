package com.openclassrooms.mddapi.mapper.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;

@Service
public interface UserMapperService {

    public User findById(Long id);
}
