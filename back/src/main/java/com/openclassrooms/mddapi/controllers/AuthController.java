package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registration successful",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = MessageResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request - Email already exists", content = @Content)})
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        try {
            User user = authService.register(request);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authService.authenticateUser(loginRequest);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new MessageResponse("Bad credentials"), HttpStatus.UNAUTHORIZED);
        }

    }
}
