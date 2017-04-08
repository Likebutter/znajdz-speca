package com.pandathree.example.Company;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue
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

    private String phone;
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
        this.phone = phone;
        this.descript = descript;
        this.avatar = avatar;
        this.avgRating = avgRating;
        this.numberJobs = numberJobs;
        this.numberOpinions = numberOpinions;
    }


}
