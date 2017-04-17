package com.pandathree.security.controller;

import com.pandathree.security.JwtAuthenticationRequest;
import com.pandathree.security.JwtTokenUtil;
import com.pandathree.security.JwtUser;
import com.pandathree.security.service.JwtAuthenticationResponse;
import com.pandathree.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Paweł on 2017-04-17.
 */

@RestController
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest){

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtAuthenticationRequest.getEmail(),
                        jwtAuthenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //if(jwtUserDetailsService.loadUserByUsername(jwtAuthenticationRequest.getEmail()).getClient() != null)
        JwtUser user =  jwtUserDetailsService.loadUserByUsername(jwtAuthenticationRequest.getEmail());
        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
