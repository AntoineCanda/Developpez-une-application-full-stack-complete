package com.openclassrooms.mddapi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

}
