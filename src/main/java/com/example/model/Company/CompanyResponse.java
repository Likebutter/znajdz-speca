package com.example.model.Company;


import com.example.model.Tag.Tag;

import java.util.List;

public class CompanyResponse {

    private Integer id;
    private String name;
    private String email;
    private String localization;
    private String phone;
    private String description;
    private float avgRating;
    private int numberJobs;
    private int numberOpinions;
    private List<Tag> tags;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer id, String name, String email, String localization, String phone, String descript, float avgRating, int numberJobs, int numberOpinions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.localization = localization;
        this.phone = phone;
        this.description = descript;
        this.avgRating = avgRating;
        this.numberJobs = numberJobs;
        this.numberOpinions = numberOpinions;
    }

    public CompanyResponse(Company company) {
        id = company.getId();
        name = company.getName();
        email = company.getEmail();
        localization = company.getLocalization();
        phone = company.getPhoneNumber();
        description = company.getDescription();
        avgRating = company.getRating();
        numberJobs = company.getNumberJobs();
        numberOpinions = company.getNumberOpinions();
    }

    public CompanyResponse(Company company, List<Tag> tags) {
        this(company);
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getNumberJobs() {
        return numberJobs;
    }

    public void setNumberJobs(int numberJobs) {
        this.numberJobs = numberJobs;
    }

    public int getNumberOpinions() {
        return numberOpinions;
    }

    public void setNumberOpinions(int numberOpinions) {
        this.numberOpinions = numberOpinions;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", localization='" + localization + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", avgRating=" + avgRating +
                ", numberJobs=" + numberJobs +
                ", numberOpinions=" + numberOpinions +
                '}';
    }
}
