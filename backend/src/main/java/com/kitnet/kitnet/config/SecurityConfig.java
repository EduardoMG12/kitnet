package com.kitnet.kitnet.config;

import com.kitnet.kitnet.filter.JwtRequestFilter;
import com.kitnet.kitnet.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // O AuthenticationManager é usado para autenticar o usuário (no login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless RESTful APIs
                .authorizeHttpRequests(authorize -> authorize
                        // Public routes (do not require authentication)
                        .requestMatchers("/api/auth/register-simple", "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/properties").permitAll()
                        .requestMatchers(HttpMethod.GET, "/properties/{id}").permitAll()
                        .requestMatchers("/api/users/verify/email/confirm").permitAll()
                        .requestMatchers("/api/users/verify/password/request").permitAll()
                        .requestMatchers("/api/users/verify/password/reset").permitAll()

                        // Protected routes (require authentication)
                        .requestMatchers(HttpMethod.POST, "/properties").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/properties/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/properties/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/users/verify/email/initiate").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/uploads/profile-picture").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/uploads/*").authenticated()

                        // All other requests require authentication by default.
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        // STATELESS session management - essential for JWT
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Adds the JwtRequestFilter before the default username/password authentication filter.
        // This ensures that our JWT filter runs first to process the token.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
