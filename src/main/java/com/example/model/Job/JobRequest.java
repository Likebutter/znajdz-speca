package com.example.model.Job;


import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public class JobRequest {

    private String title;
    private String beginDate;
    private String endDate;
    private String localization;
    private String description;
    private List<String> specializations;
    private List<MultipartFile> images;
    private Date beginDateC;
    private Date endDateC;
    //TODO: po dodaniu tokenów usunać pole clientId w JobRequest i dodać sprawdzanie użytkownika po tokenie w kontrolerze
    private Integer clientId;

    public JobRequest() {
    }

    public JobRequest(String title, String beginDate, String endDate, String localization, String descript, List<String> specializations) {
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.localization = localization;
        this.description = descript;
        this.specializations = specializations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Date getBeginDateC() {
        return beginDateC;
    }

    public void setBeginDateC(Date beginDateC) {
        this.beginDateC = beginDateC;
    }

    public Date getEndDateC() {
        return endDateC;
    }

    public void setEndDateC(Date endDateC) {
        this.endDateC = endDateC;
    }
}
