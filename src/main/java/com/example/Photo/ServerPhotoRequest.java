package com.example.Photo;


import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Opinion.Opinion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ServerPhotoRequest {

    private List<MultipartFile> images;
    private String attachedTo;
    private Job job;
    private Opinion opinion;
    private Company company;

    public ServerPhotoRequest(List<MultipartFile> images, Job job) {
        this.images = images;
        this.attachedTo = "job";
        this.job = job;
    }

    public ServerPhotoRequest(List<MultipartFile> images, Opinion opinion) {
        this.images = images;
        this.attachedTo = "opinion";
        this.opinion = opinion;
    }

    public ServerPhotoRequest(List<MultipartFile> images, Company company) {
        this.images = images;
        this.attachedTo = "company";
        this.company = company;
    }

    public List<MultipartFile> getImages() {
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
