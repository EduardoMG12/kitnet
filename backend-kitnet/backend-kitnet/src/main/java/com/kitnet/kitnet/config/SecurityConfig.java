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
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs RESTful stateless
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas públicas (não exigem autenticação)
                        .requestMatchers("/api/auth/register-simple", "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/properties").permitAll()
                        .requestMatchers(HttpMethod.GET, "/properties/{id}").permitAll()

                        // Rotas protegidas (exigem autenticação)
                        .requestMatchers(HttpMethod.POST, "/properties").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/properties/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/properties/{id}").authenticated()

                        // Todas as outras requisições exigem autenticação por padrão
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        // Gerenciamento de sessão STATELESS (sem estado) - essencial para JWT
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Adiciona o JwtRequestFilter antes do filtro padrão de autenticação por nome de usuário/senha.
        // Isso garante que nosso filtro JWT seja executado primeiro para processar o token.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
