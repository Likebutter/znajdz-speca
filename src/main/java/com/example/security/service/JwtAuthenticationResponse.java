package com.example.security.service;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Paweł on 2017-04-16.
 */
@Data
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token){
        this.token = token;
    }
}
