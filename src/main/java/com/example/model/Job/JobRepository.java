package com.example.model.Job;


import com.example.model.Client.Client;
import com.example.model.Company.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Integer>{

    List<Job> findAll();

    List<Job> findAllByClient(Client client);

    List<Job> findByCompany(Company company);

}
