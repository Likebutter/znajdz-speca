package com.pandathree.example.Submission;

import com.pandathree.example.Company.Company;
import com.pandathree.example.Job.Job;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Submission {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "companyId",
                referencedColumnName = "ID")
    private Company company;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jobId",
                referencedColumnName = "ID")
    private Job job;

    public Submission(){}

    public Submission(Company company, Job job) {
        this.company = company;
        this.job = job;
    }
}
