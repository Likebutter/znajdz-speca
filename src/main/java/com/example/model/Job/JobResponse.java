package com.example.model.Job;


import com.example.model.Client.Client;
import com.example.model.Client.ClientResponse;
import com.example.model.Company.Company;
import com.example.model.Company.CompanyResponse;
import com.example.model.Photo.PhotoResponse;
import com.example.model.Tag.Tag;

import java.util.Date;
import java.util.List;

public class JobResponse {

    private Integer id;
    private String title;
    private Date beginDate;
    private Date endDate;
    private Date addedAt;
    private String localization;
    private String description;
    private ClientResponse client;
    private CompanyResponse company;
    private List<Tag> tags;
    private List<PhotoResponse> photos;

    public JobResponse() {
    }

    public JobResponse(Integer id, String title, Date beginDate, Date endDate, Date addedAt, String localization, String descript, Client client, Company company, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addedAt = addedAt;
        this.localization = localization;
        this.description = descript;
        this.client = new ClientResponse(client);
        this.tags = tags;

        if(company != null)
            this.company = new CompanyResponse(company);
    }

    public JobResponse(Job job, List<Tag> tags) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.beginDate = job.getBeginDate();
        this.endDate = job.getEndDate();
        this.addedAt = job.getAddedAt();
        this.localization = job.getLocalization();
        this.description = job.getDescription();
        this.client = new ClientResponse(job.getClient());
        this.tags = tags;

        if(job.getCompany() != null)
            this.company = new CompanyResponse((job.getCompany()));
    }

    public JobResponse(Job job, List<Tag> tags, List<PhotoResponse> photos) {
        this(job, tags);
        this.photos = photos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientResponse getClient() {
        return client;
    }

    public void setClient(ClientResponse client) {
        this.client = client;
    }

    public CompanyResponse getCompany() {
        return company;
    }

    public void setCompany(CompanyResponse company) {
        this.company = company;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponse> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "JobResponse{" +
                "id=" + id +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", addedAt=" + addedAt +
                ", localization='" + localization + '\'' +
                ", description='" + description + '\'' +
                ", client=" + client +
                ", company=" + company +
                '}';
    }
}
