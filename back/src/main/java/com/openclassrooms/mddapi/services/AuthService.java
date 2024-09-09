package com.openclassrooms.mddapi.services;

import java.security.Principal;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openclassrooms.mddapi.exception.AuthenticationException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public String register(SignupRequest signUpRequest) throws Exception {
        if (userService.findByEmail(signUpRequest.getEmail()) != null) {
            throw new Exception("The email is already taken by another user.");
        }

        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        userService.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateJwtToken(authentication);
    }

    public String authenticateUser(LoginRequest loginRequest) {

        try {
            // Attempts to authenticate the user using the provided credentials.
            // If the credentials are invalid, a BadCredentialsException is thrown.
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtUtils.generateJwtToken(authentication);

        } catch (BadCredentialsException e) {
            // Throws an AuthentificationException with a message indicating that the credentials are invalid.
            LOGGER.error("Erreur lors de l'authentification de l'utilisateur : " + e.getMessage());
            throw new AuthenticationException(e.getMessage());
        }
    }

    public User getUserFromPrincipal(Principal principal) {
        return userService.findByEmail(principal.getName());
    }

}
