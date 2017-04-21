package com.example.Job;


import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class JobRequest {

    private String title;
    private Date beginDate;
    private Date endDate;
    private String location;
    private String description;
    private List<String> specializations;
    private List<MultipartFile> images;
    //TODO: po dodaniu tokenów usunać pole clientId w JobRequest i dodać sprawdzanie użytkownika po tokenie w kontrolerze
    private Integer clientId;

    public JobRequest() {
    }

    public JobRequest(String title, Date beginDate, Date endDate, String localization, String descript, List<String> specializations) {
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.location = localization;
        this.description = descript;
        this.specializations = specializations;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<String> specializations) {
        this.specializations = specializations;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
