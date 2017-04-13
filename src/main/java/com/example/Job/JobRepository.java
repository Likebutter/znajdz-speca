package com.example.Job;


import com.example.Client.Client;
import com.example.Company.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface JobRepository extends CrudRepository<Job, Integer>{

    List<Job> findAll();

    List<Job> findAllByClient(Client client);

    List<Job> findByCompany(Company company);

}
