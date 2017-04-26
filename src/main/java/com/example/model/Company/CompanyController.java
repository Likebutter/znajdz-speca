package com.example.model.Company;


import com.example.model.Job.Job;
import com.example.model.Job.JobRepository;
import com.example.model.Opinion.Opinion;
import com.example.model.Opinion.OpinionRepository;
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
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        if(!checkIfCorrectRequest(company))
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);

        if(companyRepository.exists(company.getId()))
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);

            companyRepository.save(company);
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id){
        Company company = companyRepository.findOne(id);
        if(company!=null)
            return new ResponseEntity<Company>(company, HttpStatus.OK);
        else
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<Company> editCompanyById(@PathVariable Integer id, @RequestBody Company company){
        if(!checkIfCorrectRequest(company))
            return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Company>(companyRepository.save(company), HttpStatus.OK);
    }

    @GetMapping("/company/{id}/jobs")
    public ResponseEntity<List<Job>> getCompanyJobsById(@PathVariable Integer id){
        Company company = companyRepository.findOne(id);
        if(company == null)
            return new ResponseEntity<List<Job>>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<List<Job>>(jobRepository.findByCompany(company),HttpStatus.OK);
    }

    @GetMapping("company/{id}/opinions")
    public ResponseEntity<List<Opinion>> getOpinionByCompanyId(@PathVariable Integer id){
        List<Job> jobs = jobRepository.findByCompany(companyRepository.findOne(id));
        List<Opinion> opinions = new ArrayList<Opinion>();
        for(Job job : jobs)
            opinions.add(opinionRepository.findByJob(job));
        return new ResponseEntity<List<Opinion>>(opinions, HttpStatus.OK);
    }

    private Boolean checkIfCorrectRequest(Company company) {

        if(company.getEmail() == null)   return false;
        if(company.getName() == null)    return false;
        if(company.getLocalization() == null)    return false;
        if(company.getPassword() == null)    return false;
        if(company.getAreaRange() == null) return false;

        return true;
    }
}
