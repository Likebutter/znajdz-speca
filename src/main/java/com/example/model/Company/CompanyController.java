package com.example.model.Company;

import com.example.model.Job.Job;
import com.example.model.Job.JobRepository;
import com.example.model.Opinion.Opinion;
import com.example.model.Opinion.OpinionRepository;
import com.example.model.Opinion.OpinionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2017-04-04.
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private OpinionRepository opinionRepository;
	
    @PostMapping("/company")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody Company company){

	       if(companyRepository.exists(company.getId()) || !checkIfCorrectRequest(company)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            companyRepository.save(company);
        return new ResponseEntity<CompanyResponse>(new CompanyResponse(company), HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Integer id){
        Company company = companyRepository.findOne(id);
        if(company!=null)
            return new ResponseEntity<CompanyResponse>(new CompanyResponse(company), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<CompanyResponse> editCompanyById(@PathVariable Integer id, @RequestBody Company newCompany){
        if(!checkIfCorrectRequest(newCompany))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Company company = companyRepository.findOne(id);

        if(company == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Company companyCheck = companyRepository.findByEmail(newCompany.getEmail());

        if(companyCheck == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(companyCheck.getId() != id)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        newCompany.setId(id);
        companyRepository.save(newCompany);
        return new ResponseEntity<CompanyResponse>(new CompanyResponse(newCompany), HttpStatus.OK);

    }

    @GetMapping("/company/{id}/jobs")
    public ResponseEntity<List<Job>> getCompanyJobsById(@PathVariable Integer id){
        Company company = companyRepository.findOne(id);
        if(company == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Job> jobs = jobRepository.findAllByCompany(company);

        if(jobs.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
    }

    @GetMapping("company/{id}/opinions")
    public ResponseEntity<List<OpinionResponse>> getOpinionByCompanyId(@PathVariable Integer id){
        Company company = companyRepository.findOne(id);
        if(company == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Job> jobs = jobRepository.findAllByCompany(company);

        if(jobs.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<OpinionResponse>>(findOpinionResponseByJobs(jobs), HttpStatus.OK);
    }

    private Boolean checkIfCorrectRequest(Company company) {

        if(company.getEmail() == null)   return false;
        if(company.getName() == null)    return false;
        if(company.getAreaRange() == null)    return false;
        if(company.getPassword() == null)    return false;
        if(company.getLocalization() == null)    return false;

        return true;
    }

    private List<OpinionResponse> findOpinionResponseByJobs(List<Job> jobs){
        List <Opinion> opinions = new ArrayList<Opinion>();

        for(Job job : jobs)
            opinions.add(opinionRepository.findByJob(job));

        List<OpinionResponse> opinionResponses = new ArrayList<OpinionResponse>();

        for(Opinion opinion : opinions)
            opinionResponses.add(new OpinionResponse(opinion));

        return opinionResponses;
    }
}
