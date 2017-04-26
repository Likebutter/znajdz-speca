package com.example.model.Opinion;


import com.example.model.Job.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OpinionRepository extends CrudRepository<Opinion, Integer>{

    Opinion findByJob(Job job);

    List<Opinion> findAllByJob(Job job);
}
