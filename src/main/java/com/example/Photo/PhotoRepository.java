package com.example.Photo;


import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Opinion.Opinion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Integer>{

    List<Photo> findAllByJob(Job job);
    List<Photo> findAllByOpinion(Opinion opinion);
    List<Photo> findAllByCompany(Company company);
}
