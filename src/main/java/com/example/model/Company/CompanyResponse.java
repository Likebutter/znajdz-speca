package com.example.model.Company;

import lombok.Data;

/**
 * Created by Paweł on 2017-04-24.
 */
@Data
public class CompanyResponse {
    private Integer id;
    private String name;
    private String email;
    private String localization;
    private String phone;
    private String descript;
    private float avgRating;
    private int numberJobs;
    private int numberOpinions;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer id, String name, String email, String localization, String phone, String descript, float avgRating, int numberJobs, int numberOpinions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.localization = localization;
        this.phone = phone;
        this.descript = descript;
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
        descript = company.getDescript();
        avgRating = company.getAvgRating();
        numberJobs = company.getNumberJobs();
        numberOpinions = company.getNumberOpinions();
    }
}
