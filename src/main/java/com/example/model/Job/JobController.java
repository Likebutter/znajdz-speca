package com.example.model.Job;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.model.AWS.S3Util;
import com.example.model.Client.Client;
import com.example.model.Client.ClientRepository;
import com.example.model.Photo.*;
import com.example.model.Specialization.Specialization;
import com.example.model.Specialization.SpecializationRepository;
import com.example.model.Tag.Tag;
import com.example.model.Tag.TagRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private SimpleDateFormat dateFormat;

    @PostMapping(value = "/job", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<JobResponse> addNewJob(@ModelAttribute JobRequest request) {

        request.setClientId(1 + new Random().nextInt(5));
        Boolean correct = checkIfCorrectRequest(request);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!convertDates(request))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        //TODO: po wprowadzeniu tokenów dodać sprawdzanie po tokenie, czy taki użytkownik jest w bazie
        Client checkedClient = clientRepository.findById(request.getClientId());
        if(checkedClient == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Job newJob = generateJobObject(request);
        jobRepository.save(newJob);
        List<Tag> tags = null;

        if(request.getSpecializations() != null) {
            tags = tagRepository.findByNameIn(request.getSpecializations());

            for (Tag tag : tags) {
                specializationRepository.save(new Specialization(tag, newJob));
            }
        }

        if((request.getImages() != null))
            if(!request.getImages().isEmpty()) {
                uploadFiles(request.getImages(), newJob);
            }

        List<Photo> gallery = photoRepository.findAllByJob(newJob);
        List<PhotoResponse> photos = new ArrayList<>();

        for(Photo photo : gallery) {
            photos.add(new PhotoResponse(photo));
        }

        JobResponse response = new JobResponse(newJob, tags, photos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/jobs")
    public ResponseEntity<List<JobResponse>> getAllJobs() {

        List<Job> jobs = jobRepository.findAll();
        List<JobResponse> response = new ArrayList<>();
        List<Tag> tags;
        List<Specialization> specs;
        List<Photo> photosList;
        List<PhotoResponse> photos;

        for(Job job : jobs) {
            specs = specializationRepository.findAllByJob(job);
            tags = new ArrayList<>();

            for(Specialization spec : specs) {
                tags.add(spec.getTag());
            }

            photosList = photoRepository.findAllByJob(job);
            photos = new ArrayList<>();

            for(Photo photo : photosList) {
                photos.add(new PhotoResponse(photo));
            }

            response.add(new JobResponse(job, tags, photos));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/job/{id}")
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
    @DeleteMapping(value = "/job/{id}")
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

        if(job.getLocalization() == null) return false;
        if(job.getTitle() == null) return false;

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

    private Boolean convertDates(JobRequest request) {

        if(request.getBeginDate() != null) {
            
            Date date;
            if (!request.getBeginDate().equals("null")) {

                try {
                    date = new Date(dateFormat.parse(request.getBeginDate()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                date = new Date(Calendar.getInstance().getTime().getTime());
            } 
            
            request.setBeginDateC(date);
        }

        if(request.getEndDate() != null) {
            
            Date date;
            if (!request.getEndDate().equals("null")) {

                try {
                    date = new Date(dateFormat.parse(request.getEndDate()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 14);
                date = new Date(calendar.getTime().getTime());
            }
            
            request.setEndDateC(date);
        }

        return true;
    }

    public JobController() {
        keyValue = 101;
        uploadPath = "https://s3-eu-west-1.amazonaws.com/pzprojektbucket/";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

}
