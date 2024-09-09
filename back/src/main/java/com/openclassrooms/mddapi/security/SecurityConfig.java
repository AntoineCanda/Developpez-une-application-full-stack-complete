package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.openclassrooms.mddapi.security.cors.CorsFilter;
import com.openclassrooms.mddapi.security.jwt.AuthTokenFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

// Le filtre qui va permettre de récupérer le Token
    private final AuthTokenFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * The {@code filterChain} method is a bean method that configures the
     * Spring Security FilterChain. It sets up the security rules for the
     * application, such as which requests require authentication, which
     * requests are permitted without authentication, and which requests are
     * denied.
     *
     * @param http the HttpSecurity object representing the current HTTP
     * security configuration
     * @return the configured HttpSecurity object with the specified security
     * rules
     * @throws Exception if an error occurs during the configuration process
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CorsFilter(), UsernamePasswordAuthenticationFilter.class).cors(withDefaults())
                .csrf((AbstractHttpConfigurer::disable));
        // Use stateless session; session won't be used to store user's state.
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configure the authorization rules for HTTP requests
        http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                // Do not authenticate these requests
                .requestMatchers(
                        antMatcher(HttpMethod.GET, "/swagger**"),
                        antMatcher(HttpMethod.GET, "/swagger-ui"),
                        antMatcher(HttpMethod.GET, "/swagger-ui/**"),
                        antMatcher(HttpMethod.GET, "/v3/api-docs/**"),
                        antMatcher(HttpMethod.GET, "/v3/api-docs**"),
                        antMatcher(HttpMethod.POST, "/auth/login"),
                        antMatcher(HttpMethod.POST, "/auth/register")
                )
                .permitAll()
                // Authenticate these requests
                .requestMatchers(antMatcher("/api/**"))
                .authenticated()
                // Deny all other requests
                .anyRequest().denyAll());

        // Configure the exception handling for unauthenticated requests
        http.exceptionHandling(handlingConfigurer -> handlingConfigurer
                .authenticationEntryPoint((request, response, exception) -> response
                .sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage())));

        http.authenticationProvider(authenticationProvider);
        // Add a filter to validate the tokens with every authenticated request
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Return the configured HttpSecurity object
        return http.build();
    }
}
