package com.openclassrooms.mddapi.controllers;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registration successful",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request - Email already exists", content = @Content)})
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request, BindingResult binding) {
        LOGGER.info("Processing registration request");

        if (binding.hasErrors()) {
            String errorMessages = binding.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n"));
            LOGGER.error("Validation failed for register request for user {}: \n{}", request.getEmail(), errorMessages);
            throw new BadRequestException(errorMessages);
        }

        try {
            String jwt = authService.register(request);

            return ResponseEntity.ok(new JwtResponse(jwt));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authentication", description = "Authenticate a user and returns a Bearer token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult binding) {
        LOGGER.info("Processing login request");

        if (binding.hasErrors()) {
            String errorMessages = binding.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n"));
            LOGGER.error("Validation failed for login request for user {}: \n{}", loginRequest.getEmail(), errorMessages);
            throw new BadRequestException(errorMessages);
        }

        try {
            String jwt = authService.authenticateUser(loginRequest);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new MessageResponse("Bad credentials"), HttpStatus.UNAUTHORIZED);
        }

    }
}
