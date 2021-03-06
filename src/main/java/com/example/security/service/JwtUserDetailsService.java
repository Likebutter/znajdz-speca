package com.example.security.service;

import com.example.logger.MyLogger;
import com.example.model.Client.Client;
import com.example.model.Client.ClientRepository;
import com.example.model.Company.Company;
import com.example.model.Company.CompanyRepository;
import com.example.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Paweł on 2017-04-16.
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username);
        Company company = companyRepository.findByEmail(username);

        if(client == null && company == null)
            throw new UsernameNotFoundException("No user found with username: " + username);

        else if(client == null){
            MyLogger.log.info(this.getClass().getName() + " Creating new JwtCompany");
            return new JwtUser(company);
        }

        MyLogger.log.info(this.getClass().getName() + " Creating new JwtClient");
        return new JwtUser(client);
    }
}
