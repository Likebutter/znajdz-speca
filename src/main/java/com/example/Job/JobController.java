package com.example.Job;

import com.example.Client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Calendar;

@RestController
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ClientRepository clientRepository;


    @PostMapping(value = "/job")
    public ResponseEntity<JobResponse> addNewJob(@RequestBody JobRequest request) {

        Boolean correct = checkIfCorrectRequest(request);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //TODO: po wprowadzeniu tokenów dodać sprawdzanie, czy taki użytkownik jest w bazie
        Job newJob = generateJobObject(request);
        jobRepository.save(newJob);
        JobResponse response = new JobResponse(newJob);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Boolean checkIfCorrectRequest(JobRequest job) {

        if(job.getBeginDate() == null) return false;
        if(job.getEndDate() == null) return false;
        if(job.getLocalization() == null) return false;

        return true;
    }

    private Job generateJobObject(JobRequest request) {

        Job newJob = new Job(request);
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        newJob.setAddedAt(date);
        newJob.setVisible(true);
        //TODO: Powiązać klienta po tokenie i wyrzucić clientRepository z tego kontrolera
        newJob.setClient(clientRepository.findById(1));

        return newJob;
    }

}
