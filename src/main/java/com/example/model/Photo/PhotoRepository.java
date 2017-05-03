package com.example.model.Photo;

import com.example.model.Company.Company;
import com.example.model.Job.Job;
import com.example.model.Opinion.Opinion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {

    List<Photo> findAll();

    List<Photo> findAllByJob(Job job);

    List<Photo> findAllByCompany(Company company);

    List<Photo> findAllByOpinion(Opinion opinion);
}
