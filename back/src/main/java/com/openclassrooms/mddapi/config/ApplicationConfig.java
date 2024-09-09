package com.openclassrooms.mddapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.mddapi.security.services.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    UserDetailsServiceImpl userDetailsService;

    /**
     * New PasswordEncoder instance
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean method to create an instance of {@link AuthenticationProvider}. This
     * service is responsible for loading user-specific data from the database.
     *
     * @return an instance of {@link AuthenticationProvider} that uses the
     * provided {@link userDetailsService}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Bean method to create an instance of {@link AuthenticationManager}. This
     * class is responsible for authenticating users by delegating to the
     * {@link AuthenticationProvider}.
     *
     * @param configuration The {@link AuthenticationConfiguration} instance.
     * @return An instance of {@link AuthenticationManager}.
     * @throws Exception If there is an error while creating the authentication
     * manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
