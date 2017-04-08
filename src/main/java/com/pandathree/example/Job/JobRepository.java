package com.pandathree.example.Job;

import com.pandathree.example.Company.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Integer>{
    public List<Job> findByCompany(Company company);
}
