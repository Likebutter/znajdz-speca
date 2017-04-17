package com.pandathree.security;

import com.pandathree.example.Client.Client;
import com.pandathree.example.Company.Company;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Paweł on 2017-04-16.
 */
@Data
public class JwtUser implements UserDetails{

    private Client client;
    private Company company;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Client client) { this.client = client; }

    public JwtUser(Company company){
        this.company = company;
    }

    public JwtUser(Client client, Collection<? extends GrantedAuthority> authorities){
        this.client = client;
        this.authorities = authorities;
    }

    public JwtUser(Company company, Collection<? extends GrantedAuthority> authorities){
        this.company = company;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        if(client != null) return client.getPassword();
        else return company.getPassword();
    }

    @Override
    public String getUsername() {
        if(client != null) return client.getEmail();
        else return company.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
