package com.example.model.Photo;


import com.example.model.Company.Company;
import com.example.model.Job.Job;
import com.example.model.Opinion.Opinion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Integer>{

    List<Photo> findAllByJob(@Param(value = "Job") Job job);
    List<Photo> findAllByOpinion(Opinion opinion);
    List<Photo> findAllByCompany(Company company);
}
