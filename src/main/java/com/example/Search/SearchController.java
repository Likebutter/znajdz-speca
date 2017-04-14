package com.example.Search;


import com.example.Job.CustomJobDao;
import com.example.Job.Job;
import com.example.Job.JobResponse;
import com.example.Specialization.Specialization;
import com.example.Specialization.SpecializationRepository;
import com.example.Tag.Tag;
import com.example.Tag.TagRepository;
import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private GeoApiContext context;

    @Autowired
    private CustomJobDao customJobDao;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private TagRepository tagRepository;

    @PostMapping(value = "/search/jobs")
    public ResponseEntity<List<JobResponse>> searchForJobs(@RequestBody SearchJobRequest request) {

        List<Job> foundJobs = customJobDao.buildAndExecuteSqlQuery(request);

        if(foundJobs == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if(foundJobs.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<JobResponse> response = generateJobResponse(foundJobs);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<JobResponse> generateJobResponse(List<Job> jobs) {

        List<JobResponse> response = new ArrayList<>();

        for(Job job : jobs) {

            List<Specialization> specs = specializationRepository.findAllByJob(job);
            List<Tag> jobTags = new ArrayList<>();

            for(Specialization spec : specs) {
               jobTags.add(spec.getTag());
            }

            response.add(new JobResponse(job, jobTags));
        }

        return response;
    }

}
