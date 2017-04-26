package com.example.model.Opinion;


import com.example.model.Job.Job;
import org.springframework.data.repository.CrudRepository;

public interface OpinionRepository extends CrudRepository<Opinion, Integer>{
    public Opinion findByJob(Job job);
}
