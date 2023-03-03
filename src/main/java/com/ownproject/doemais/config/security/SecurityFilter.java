package com.ownproject.doemais.config.security;

import com.ownproject.doemais.models.usuario.Usuario;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenJwt = recuperarToken(request);
        if(tokenJwt != null){
            String subject = tokenService.getSubject(tokenJwt);
            Usuario usuario = usuarioRepository.findByEmail(subject).orElseThrow(
                    () -> new RuntimeException("Token JWT possui usuário ínvalido"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                    null,
                    usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private static String recuperarToken(HttpServletRequest request) {

        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ","");
        }
        return null;
    }
}
