package com.pandathree.example.Specialization;

import com.pandathree.example.Company.Company;
import com.pandathree.example.Job.Job;
import com.pandathree.example.Tag.Tag;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "taggroup")
@Data
public class Specialization {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "tagId",
                referencedColumnName = "ID")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "comapnyID",
                referencedColumnName = "ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "jobId",
                referencedColumnName = "ID")
    private Job job;

    public Specialization(){}

    public Specialization(Tag tag, Company company, Job job) {
        this.tag = tag;
        this.company = company;
        this.job = job;
    }
}
