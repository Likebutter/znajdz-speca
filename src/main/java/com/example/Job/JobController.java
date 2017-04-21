package com.example.Job;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.AWS.S3Util;
import com.example.Client.Client;
import com.example.Client.ClientRepository;
import com.example.Photo.*;
import com.example.Specialization.Specialization;
import com.example.Specialization.SpecializationRepository;
import com.example.Tag.Tag;
import com.example.Tag.TagRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private S3Util s3util;

    private Integer keyValue;
    private String uploadPath;

    @PostMapping(value = "/job", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<JobResponse> addNewJob(@ModelAttribute JobRequest request) {

        request.setClientId(1 + new Random().nextInt(5));
        Boolean correct = checkIfCorrectRequest(request);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //TODO: po wprowadzeniu tokenów dodać sprawdzanie po tokenie, czy taki użytkownik jest w bazie
        Client checkedClient = clientRepository.findById(request.getClientId());
        if(checkedClient == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Job newJob = generateJobObject(request);
        jobRepository.save(newJob);
        List<Tag> tags = tagRepository.findByNameIn(request.getSpecializations());

        for(Tag tag: tags) {
            specializationRepository.save(new Specialization(tag, newJob));
        }

        if((request.getImages() != null))
            if(!request.getImages().isEmpty()) {
                uploadFiles(request.getImages(), newJob);
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

        List<Photo> gallery = photoRepository.findAllByJob(job);
        List<PhotoResponse> photos = new ArrayList<>();

        for(Photo photo : gallery) {
            photos.add(new PhotoResponse(photo));
        }

        JobResponse response = new JobResponse(job, tags, photos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: Po dodaniu tokenów zakodować sprawdzanie, czy Job należy do klienta określonego przez token
    @DeleteMapping(value = "job/{id}")
    public ResponseEntity<JobResponse> deleteJob(@PathVariable Integer id) {

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        /*
        Client client = clientRepository.findById(id);

        if(job.getClient().getId() != client.getId()) {
            return new ResponseEntity<JobResponse>(HttpStatus.FORBIDDEN);
        }
        */

        if(job.getCompany() != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Specialization> jobTags = specializationRepository.findAllByJob(job);
        specializationRepository.delete(jobTags);

        List<Photo> photos = photoRepository.findAllByJob(job);
        photoRepository.delete(photos);

        jobRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean checkIfCorrectRequest(JobRequest job) {

        if(job.getBeginDate() == null) return false;
        if(job.getEndDate() == null) return false;
        if(job.getLocation() == null) return false;
        if((job.getSpecializations() == null) || (job.getSpecializations().isEmpty())) return false;
        //if(job.getClientId() == null) return false;

        return true;
    }

    private Job generateJobObject(JobRequest request) {

        Job newJob = new Job(request);
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        newJob.setAddedAt(date);
        newJob.setVisible(true);
        //TODO: Powiązać klienta po tokenie, wiązać job z klientem z tokena
        newJob.setClient(clientRepository.findById(request.getClientId()));

        return newJob;
    }

    private void uploadFiles(List<MultipartFile> files, Job job) {

        for(MultipartFile file : files) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uploadKey = "znajdzspeca/images/" + keyValue + "." + extension;

            PutObjectResult putObjectResult;

            try {
                putObjectResult = s3util.upload(file, uploadKey);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Photo photo = new Photo(job, uploadPath + uploadKey);
            photoRepository.save(photo);
            keyValue++;
        }
    }

    public JobController() {
        keyValue = 101;
        uploadPath = "https://s3-eu-west-1.amazonaws.com/pzprojektbucket/";
    }
}
