package com.pandathree.example.Opinion;


import com.pandathree.example.Job.Job;
import org.springframework.data.repository.CrudRepository;

public interface OpinionRepository extends CrudRepository<Opinion, Integer>{
    public Opinion findByJob(Job job);
}
