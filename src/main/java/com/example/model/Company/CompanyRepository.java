package com.example.model.Company;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer>{
    public List<Company> findByEmailOrName(String email, String name);
    public Company findByEmail(String email);
}
