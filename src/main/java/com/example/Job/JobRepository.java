package com.example.Job;


import com.example.Client.Client;
import com.example.Company.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Integer>{

    List<Job> findAllByClient(Client client);

    List<Job> findByCompany(Company company);

}
