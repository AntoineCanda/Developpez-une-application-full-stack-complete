package com.openclassrooms.mddapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthService authService;

    @Mock
    private Authentication authentication;

    private LoginRequest loginRequest;
    private SignupRequest signUpRequest;

    @BeforeEach
    public void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password");

        signUpRequest = new SignupRequest();
        signUpRequest.setEmail("john.doe@example.com");
        signUpRequest.setUsername("John Doe");
        signUpRequest.setPassword("Test!1234");

    }

    @Test
    @DisplayName("Authenticate user → Success")
    public void testAuthenticateUser_Success() {
        // Arrange

        when(authService.authenticateUser(loginRequest)).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwtToken");

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse);
        assertThat(jwtResponse.getToken()).isEqualTo("jwtToken");
    }

    /*     @Test
    @DisplayName("Authenticate user with invalid credentials → Bad credentials")
    public void testAuthenticateUser_withInvalidCredentials_badCredentials() {
        // Arrange
        when(authService.authenticateUser(loginRequest)).thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authController.login(loginRequest);
        });
        assertThat(exception).isInstanceOf(BadCredentialsException.class);
        assertThat(exception.getMessage()).isEqualTo("Invalid credentials");
        verifyNoInteractions(jwtUtils);
    } */
    @Test
    @DisplayName("Register new user → Success")
    public void testRegisterNewUser_Success() throws Exception {

        // Arrange
        User user = User.builder().id(1L).username("John Doe").password("Test!1234").email("john.doe@example.com").build();
        when(authService.register(signUpRequest)).thenReturn(user);

        // Act
        ResponseEntity<?> response = authController.register(signUpRequest);
        MessageResponse messageResponse = (MessageResponse) response.getBody();

        // Assert
        assertNotNull(response.getBody());
        assertInstanceOf(MessageResponse.class, response.getBody());
        assertThat(messageResponse.getMessage()).isEqualTo("User registered successfully!");
    }

    /*  @Test
    @DisplayName("Register new user with already used email → Bad Request")
    public void testRegisterWithEmailAlreadyUse_BadRequest() throws Exception {
        // Arrange
        when(authService.register(signUpRequest)).thenThrow(new Exception("The email is already taken by another user."));

        Exception exception = assertThrows(Exception.class, () -> {
            authController.register(signUpRequest);
        });

        // Assert
        assertThat(exception).isInstanceOf(Exception.class);
        assertThat(exception.getMessage()).isEqualTo("The email is already taken by another user.");
    } */
}
