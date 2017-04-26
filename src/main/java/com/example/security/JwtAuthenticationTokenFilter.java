package com.example.security;

import com.example.logger.MyLogger;
import com.example.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Paweł on 2017-04-15.
 */

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MyLogger.log.info(this.getClass().getName() + " FILTERING");

        String authToken = request.getHeader(this.tokenHeader);
        MyLogger.log.info(this.getClass().getName() + " FILTERING token header: " + request.getHeader(this.tokenHeader));
        String email = jwtTokenUtil.getEmailFromToken(authToken);
        MyLogger.log.info(this.getClass().getName() + " FILTERING email from token: " + jwtTokenUtil.getEmailFromToken(authToken));

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            jwtUserDetailsService.loadUserByUsername(email);
        }

        filterChain.doFilter(request, response);
    }
}
