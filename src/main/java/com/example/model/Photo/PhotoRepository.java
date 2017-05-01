package com.example.model.Photo;


import com.example.model.Job.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Integer>{
   List<Photo> findAllByJob(Job job);
}
