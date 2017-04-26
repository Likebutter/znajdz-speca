package com.example.model.Photo;

import com.example.model.Company.Company;
import com.example.model.Job.Job;
import com.example.model.Opinion.Opinion;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name = "public", sequenceName = "photo_seq", initialValue = 1, allocationSize = 1)
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

    public Photo(Job job, String photoURL) {
        this.job = job;
        this.photoURL = photoURL;
    }

    public Photo(Opinion opinion, String photoURL) {
        this.opinion = opinion;
        this.photoURL = photoURL;
    }

    public Photo(Company company, String photoURL) {
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
