package com.pandathree.security.service;

import com.pandathree.example.Client.Client;
import com.pandathree.example.Client.ClientRepository;
import com.pandathree.example.Company.Company;
import com.pandathree.example.Company.CompanyRepository;
import com.pandathree.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Paweł on 2017-04-16.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService{

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username);
        Company company = companyRepository.findByEmail(username);

        if(client == null && company == null)
            throw new UsernameNotFoundException("No user found with username: " + username);

        else if(client == null)
            return new JwtUser(company);

        return new JwtUser(client);
    }
}
