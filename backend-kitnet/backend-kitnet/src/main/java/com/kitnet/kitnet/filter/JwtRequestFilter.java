package com.kitnet.kitnet.filter;

import com.kitnet.kitnet.service.CustomUserDetailsService;
import com.kitnet.kitnet.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.debug("Requisição recebida para o filtro JWT: {}", request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.debug("Token JWT extraído: {}", jwt);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.debug("Username extraído do JWT: {}", username);
            } catch (Exception e) {
                logger.warn("Erro ao extrair username do JWT ou token inválido: {}", e.getMessage());
            }
        } else {
            logger.debug("Cabeçalho Authorization não encontrado ou não começa com Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                userDetails = this.userDetailsService.loadUserByUsername(username);
                logger.debug("UserDetails carregado para: {}", username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.debug("Usuário {} autenticado e SecurityContextHolder populado.", username);
                } else {
                    logger.warn("Validação do token JWT falhou para o usuário: {}", username);
                }
            } catch (Exception e) {
                logger.warn("Erro ao carregar UserDetails ou validar token para {}: {}", username, e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
