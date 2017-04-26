package com.example.security;

import com.example.model.Client.Client;
import com.example.model.Company.Company;

/**
 * Created by Paweł on 2017-04-17.
 */

public class JwtUserFactory {

    private JwtUserFactory(){};

    public static JwtUser createJwtUser(Client client){
        return new JwtUser(client);
    }

    public static JwtUser createJwtUser(Company company){
        return new JwtUser(company);
    }
}
