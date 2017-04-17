package com.example.Opinion;


import com.example.Job.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OpinionRepository extends CrudRepository<Opinion, Integer>{

    Opinion findByJob(Job job);

}
