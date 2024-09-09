package com.openclassrooms.mddapi.controllers;

import java.security.Principal;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UpdateUserDto;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get a User by its id")
    @GetMapping("/me")
    public ResponseEntity<?> getUser(@RequestParam("id") final Long id) {
        try {
            User user = userService.findById(id);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update a User")
    @PutMapping("update/{id}")
    public ResponseEntity<?> update(Principal principal, @RequestBody UpdateUserDto updateUserDto) {
        try {
            User user = userService.findByEmail(principal.getName());
            user.setEmail(updateUserDto.getEmail());
            user.setUsername(updateUserDto.getUserName());
            User updatedUser = userService.save(user);

            return ResponseEntity.ok().body(userMapper.toDto(updatedUser));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Subscribe to a subject")
    @PostMapping("subscribe/{subjectId}")
    public ResponseEntity<?> subscribe(Principal principal, @PathVariable("subjectId") Long subjectId) {
        try {
            User user = userService.findByEmail(principal.getName());
            User updatedUser = userService.subscribe(user, subjectId);

            return ResponseEntity.ok().body(userMapper.toDto(updatedUser));
        } catch (NumberFormatException | BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Unsubscribe from a subject")
    @DeleteMapping("unsubscribe/{subjectId}")
    public ResponseEntity<?> unsubscribe(Principal principal, @PathVariable("subjectId") Long subjectId) {
        try {
            User user = userService.findByEmail(principal.getName());
            User updatedUser = userService.unsubscribe(user, subjectId);

            return ResponseEntity.ok().body(userMapper.toDto(updatedUser));
        } catch (NumberFormatException | BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
