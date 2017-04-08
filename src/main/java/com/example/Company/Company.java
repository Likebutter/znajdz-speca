package com.example.Company;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
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

    private String phone;
    private String descript;
    private String avatar;
    private float avgRating;
    private int numberJobs;
    private int numberOpinions;

    public Company() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Integer getAreaRange() {
        return areaRange;
    }

    public void setAreaRange(Integer areaRange) {
        this.areaRange = areaRange;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", descript='" + descript + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avgRating=" + avgRating +
                ", numberJobs=" + numberJobs +
                ", numberOpinions=" + numberOpinions +
                '}';
    }
}
