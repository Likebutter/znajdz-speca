package com.pandathree.security;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Paweł on 2017-04-16.
 */
@Data
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String email;
    private String password;

    public JwtAuthenticationRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
