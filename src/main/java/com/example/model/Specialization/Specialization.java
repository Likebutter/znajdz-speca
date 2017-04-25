package com.example.model.Specialization;


import com.example.model.Company.Company;
import com.example.model.Job.Job;
import com.example.model.Tag.Tag;

import javax.persistence.*;

@Entity
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name = "public", sequenceName = "specialization_seq", initialValue = 1, allocationSize = 1)
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

    public Specialization() {
    }

    public Specialization(Tag tag, Job job) {
        this.tag = tag;
        this.job = job;
    }

    public Specialization(Tag tag, Company company) {
        this.tag = tag;
        this.company = company;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "tag=" + tag +
                ", company=" + company +
                ", job=" + job +
                '}';
    }
}
