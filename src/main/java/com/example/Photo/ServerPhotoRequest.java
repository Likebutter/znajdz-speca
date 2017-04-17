package com.example.Photo;


import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Opinion.Opinion;

import java.util.List;

public class ServerPhotoRequest {

    private List<FileData> images;
    private String attachedTo;
    private Job job;
    private Opinion opinion;
    private Company company;

    public ServerPhotoRequest(List<FileData> images, Job job) {
        this.images = images;
        this.attachedTo = "job";
        this.job = job;
    }

    public ServerPhotoRequest(List<FileData> images, Opinion opinion) {
        this.images = images;
        this.attachedTo = "opinion";
        this.opinion = opinion;
    }

    public ServerPhotoRequest(List<FileData> images, Company company) {
        this.images = images;
        this.attachedTo = "company";
        this.company = company;
    }

    public List<FileData> getImages() {
        return images;
    }

    public String getAttachedTo() {
        return attachedTo;
    }

    public Job getJob() {
        return job;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public Company getCompany() {
        return company;
    }
}
