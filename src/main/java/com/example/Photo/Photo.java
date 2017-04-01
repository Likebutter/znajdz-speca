package com.example.Photo;

import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Opinion.Opinion;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Photo {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "jobId",
                referencedColumnName = "ID")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "opinionId",
                referencedColumnName = "ID")
    private Opinion opinion;

    @ManyToOne
    @JoinColumn(name = "companyId",
                referencedColumnName = "ID")
    private Company company;

    @NotNull
    private String photoURL;

    public Photo() {
    }

    public Photo(String photoURL) {
        this.photoURL = photoURL;
    }

    public Photo(Job job, Opinion opinion, Company company, String photoURL) {
        this.job = job;
        this.opinion = opinion;
        this.company = company;
        this.photoURL = photoURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", job=" + job +
                ", opinion=" + opinion +
                ", company=" + company +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}
