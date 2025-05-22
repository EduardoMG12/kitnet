//package com.kitnet.kitnet.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}


package com.kitnet.kitnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita a segurança web do Spring
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes e APIs stateless
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público aos endpoints de registro e login de usuários
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        // Permite acesso público aos endpoints de propriedades (se desejar que sejam públicos)
                        // Se /properties deve ser público para GET, mas restrito para POST/PUT/DELETE
                        .requestMatchers("/properties").permitAll() // Exemplo: permite GET para /properties
                        .requestMatchers("/properties/**").permitAll() // Permite acesso a todos os subcaminhos de /properties
                        // Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                );
        // Você pode adicionar configurações de login aqui, se necessário (ex: formLogin, httpBasic)
        // .httpBasic(Customizer.withDefaults()); // Habilita autenticação Basic HTTP
        // .formLogin(Customizer.withDefaults()); // Habilita autenticação por formulário

        return http.build();
    }
}
