package com.example.model.Job;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.model.AWS.S3Util;
import com.example.model.Client.Client;
import com.example.model.Client.ClientRepository;
import com.example.model.Company.Company;
import com.example.model.Company.CompanyRepository;
import com.example.model.Company.CompanyResponse;
import com.example.model.Opinion.Opinion;
import com.example.model.Opinion.OpinionRepository;
import com.example.model.Opinion.OpinionRequest;
import com.example.model.Photo.*;
import com.example.model.Specialization.Specialization;
import com.example.model.Specialization.SpecializationRepository;
import com.example.model.Submission.Submission;
import com.example.model.Submission.SubmissionRepository;
import com.example.model.Tag.Tag;
import com.example.model.Tag.TagRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private CompanyRepository companyRepository;

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private S3Util s3util;

    private Integer keyValue;
    private String uploadPath;
    private SimpleDateFormat dateFormat;

    @PostMapping(value = "/job", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<JobResponse> addNewJob(@ModelAttribute JobRequest request) {

        Client client = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(client == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        request.setClientId(client.getId());

        Boolean correct = checkIfCorrectRequest(request);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!convertDates(request))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

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

    @PostMapping(value = "/commission/{companyId}", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<JobResponse> addNewCommission(@ModelAttribute JobRequest request, @PathVariable Integer companyId){


        Client client = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(client == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Company company = companyRepository.findOne(companyId);

        if(company == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        request.setClientId(client.getId());

        Boolean correct = checkIfCorrectRequest(request);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!convertDates(request))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        Job newJob = generateJobObject(request);
        newJob.setVisible(false);
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

        Submission newSubmission = new Submission(company, newJob);
        submissionRepository.save(newSubmission);

        JobResponse response = new JobResponse(newJob,tags);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/job/{id}/edit", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<JobResponse> modifyJob(@ModelAttribute JobRequest request, @PathVariable Integer id) {

        if(!checkIfCorrectRequest(request))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Client client = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(client == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!job.getClient().getEmail().equals(client.getEmail()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(job.getCompany() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!convertDates(request))
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        List<Specialization> specs = specializationRepository.findAllByJob(job);
        specializationRepository.delete(specs);

        job.setTitle(request.getTitle());
        job.setBeginDate(request.getBeginDateC());
        job.setEndDate(request.getEndDateC());
        job.setLocalization(request.getLocalization());
        job.setDescription(request.getDescription());

        jobRepository.save(job);

        List<Tag> tags = null;

        if(request.getSpecializations() != null) {
            tags = tagRepository.findByNameIn(request.getSpecializations());

            for (Tag tag : tags) {
                specializationRepository.save(new Specialization(tag, job));
            }
        }

        JobResponse response = new JobResponse(job, tags);

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


        Client client = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!job.getClient().getName().equals(client.getName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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

    @GetMapping(value = "/job/{id}/submissions")
    public ResponseEntity<List<CompanyResponse>> getSubmissionsForJob(@PathVariable Integer id) {

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!job.getClient().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Submission> submissions = submissionRepository.findAllByJob(job);
        List<CompanyResponse> response = new ArrayList<>();

        for(Submission submission : submissions) {
            if(submission.getAccepted() != null)
                if(submission.getAccepted())
                    response.add(new CompanyResponse(submission.getCompany()));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping(value = "/job/{id}")
    public ResponseEntity<JobResponse> applyForJob(@PathVariable Integer id){

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()) != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(job.getCompany() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!validateDate(job))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Company company = companyRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if(company == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Submission sub = submissionRepository.findByJobAndCompany(job, company);

        if(sub == null)
            sub = new Submission(company, job, true);
        else
            sub.setAccepted(true);

        submissionRepository.save(sub);

        return new ResponseEntity<>(new JobResponse(job,returnTagListBySpecializations(job)),
                HttpStatus.OK);

    }

    @PutMapping(value = "/job/{id}/company/{companyId}")
    public ResponseEntity<?> chooseCompanyForJob(@PathVariable Integer id, @PathVariable Integer companyId) {

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Client client = clientRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(client == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!job.getClient().getEmail().equals(client.getEmail()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Company company = companyRepository.findOne(companyId);

        if(company == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Submission offer = submissionRepository.findByJobAndCompany(job, company);

        if(offer != null)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        submissionRepository.save(new Submission(company, job, null));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value= "/job/{id}/reject")
    public ResponseEntity<?> rejectOffer(@PathVariable Integer id) {

        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Company company = companyRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(company == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Submission offer = submissionRepository.findByJobAndCompany(job, company);

        if(offer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(offer.getAccepted() != null)
            if(offer.getAccepted())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        offer.setAccepted(false);
        submissionRepository.save(offer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/job/{id}/opinion")
    public ResponseEntity<JobResponse> addOpinionToJob(@PathVariable Integer id, @RequestBody OpinionRequest opinionRequest){
        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(!job.getClient().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(job.getCompany() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!validateOpinionRequest(opinionRequest, job))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(opinionRepository.findByJob(job) != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        opinionRepository.save(new Opinion(opinionRequest, job));

        // TODO: referencja do firmy
        return new ResponseEntity<>(new JobResponse(job,returnTagListBySpecializations(job))
                ,HttpStatus.OK);
    }

    @PutMapping(value = "/job/{id}/accept/{companyId}")
    public ResponseEntity<JobResponse> acceptJob(@PathVariable Integer id, @PathVariable Integer companyId){
        Job job = jobRepository.findOne(id);

        if(job == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(companyRepository.findOne(companyId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(!job.getClient().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!job.getVisible())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(job.getCompany() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        job.setCompany(companyRepository.findOne(companyId));
        job.setVisible(false);

        jobRepository.save(job);

        List<Submission> subs = submissionRepository.findAllByJob(job);
        submissionRepository.delete(subs);

        return new ResponseEntity<>(new JobResponse(job,returnTagListBySpecializations(job))
                ,HttpStatus.OK);
    }

    private boolean validateOpinionRequest(OpinionRequest opinionRequest, Job job){

        //TODO: sprawdzenie czy rate jest w odpowiedznim przedziale itp
        //if(opinionRequest.getRate() between min_rate max_rate)

        return true;
    }

    private List<Tag> returnTagListBySpecializations(Job job) {
        List<Specialization> specializations = specializationRepository.findAllByJob(job);
        List<Tag> tags = new ArrayList<>();

        for(Specialization specialization : specializations)
            tags.add(specialization.getTag());

        return tags;
    }

    private boolean validateDate(Job job) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());

        if(job.getBeginDate() != null)
            if(job.getBeginDate().before(date))
                return false;

        if(job.getEndDate() != null)
            if(job.getEndDate().before(date))
                return false;

        return true;
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
