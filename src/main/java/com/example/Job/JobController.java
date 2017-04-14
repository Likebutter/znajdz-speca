package com.example.Job;

import com.example.Client.ClientRepository;
import com.example.Specialization.Specialization;
import com.example.Specialization.SpecializationRepository;
import com.example.Tag.Tag;
import com.example.Tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @PostMapping(value = "/job")
    public ResponseEntity<JobResponse> addNewJob(@RequestBody JobRequest request) {

        Boolean correct = checkIfCorrectRequest(request);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //TODO: po wprowadzeniu tokenów dodać sprawdzanie, czy taki użytkownik jest w bazie
        Job newJob = generateJobObject(request);
        jobRepository.save(newJob);
        List<Tag> tags = tagRepository.findByNameIn(request.getTags());

        for(Tag tag: tags) {
            specializationRepository.save(new Specialization(tag, newJob));
        }
        JobResponse response = new JobResponse(newJob, tags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "job/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable Integer id) {

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Specialization> specs = specializationRepository.findAllByJob(job);
        List<Tag> tags = new ArrayList<>();

        for(Specialization spec : specs) {
            tags.add(spec.getTag());
        }

        JobResponse response = new JobResponse(job, tags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: Po dodaniu tokenów zakodować sprawdzanie, czy Job należy do klienta określonego przez token
    @DeleteMapping(value = "job/{id}")
    public ResponseEntity<JobResponse> deleteJob(@PathVariable Integer id) {

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        /*
        Client client = clientRepository.findByEmail(mail);

        if(job.client.getId() != client.getId()) {
            return new ResponseEntity<JobResponse>(HttpStatus.FORBIDDEN);
        }
         */

        if(job.getCompany() != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        jobRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean checkIfCorrectRequest(JobRequest job) {

        if(job.getBeginDate() == null) return false;
        if(job.getEndDate() == null) return false;
        if(job.getLocalization() == null) return false;
        if((job.getTags() == null) || (job.getTags().isEmpty())) return false;

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
