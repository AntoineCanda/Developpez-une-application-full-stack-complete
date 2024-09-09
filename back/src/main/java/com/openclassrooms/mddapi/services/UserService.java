package com.openclassrooms.mddapi.services;

import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean isUserAdmin(String username) {
        boolean isAdmin = false;
        User user = userRepository.findByEmail(username).orElse(null);
        if (user != null) {
            isAdmin = user.isAdmin();
        }
        return isAdmin;
    }

    public User subscribe(User user, Long subjectId) throws BadRequestException, NotFoundException {
        boolean alreadySubscribed = user.getSubjects().stream().anyMatch(o -> o.getId().equals(subjectId));
        if (alreadySubscribed) {
            throw new BadRequestException();
        }

        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject == null) {
            throw new NotFoundException();
        }

        user.getSubjects().add(subject);

        return userRepository.save(user);
    }

    public User unsubscribe(User user, Long subjectId) throws BadRequestException, NotFoundException {
        boolean alreadySubscribed = user.getSubjects().stream().anyMatch(o -> o.getId().equals(subjectId));
        if (!alreadySubscribed) {
            throw new BadRequestException();
        }

        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject == null) {
            throw new NotFoundException();
        }

        user.setSubjects(user.getSubjects().stream().filter(t -> !t.getId().equals(subjectId)).collect(Collectors.toList()
        ));

        return userRepository.save(user);
    }
}
