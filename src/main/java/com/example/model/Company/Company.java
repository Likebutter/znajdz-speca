package com.example.model.Company;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name = "public", sequenceName = "company_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String localization;

    @NotNull
    private Integer areaRange;

    private String phoneNumber;
    private String descript;
    private String avatar;
    private float avgRating;
    private int numberJobs;
    private int numberOpinions;

    public Company(){};

    public Company(String name, String email, String password, String localization, Integer areaRange) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.localization = localization;
        this.areaRange = areaRange;
    }

    public Company(String name, String email, String password, String localization, Integer areaRange, String phone, String descript, String avatar, float avgRating, int numberJobs, int numberOpinions) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.localization = localization;
        this.areaRange = areaRange;
        this.phoneNumber = phone;
        this.descript = descript;
        this.avatar = avatar;
        this.avgRating = avgRating;
        this.numberJobs = numberJobs;
        this.numberOpinions = numberOpinions;
    }


}
