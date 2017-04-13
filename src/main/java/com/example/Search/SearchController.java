package com.example.Search;


import com.example.Job.CustomJobDao;
import com.example.Job.Job;
import com.example.Job.JobResponse;
import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private GeoApiContext context;

    @Autowired
    private CustomJobDao customJobDao;

    @PostMapping(value = "/search/jobs")
    public ResponseEntity<List<Job>> searchForJobs(@RequestBody SearchJobRequest request) {

        String sqlQuery = customJobDao.buildSqlQuery(request);
        System.out.println("*******************************");
        System.out.println(sqlQuery);
        System.out.println("*******************************");

        List<Job> foundJobs = customJobDao.buildAndExecuteSqlQuery(request);

        return new ResponseEntity<>(foundJobs, HttpStatus.OK);
    }

}
