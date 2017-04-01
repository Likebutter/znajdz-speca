package com.example.Area;

import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Localization.Localization;

import javax.persistence.*;

@Entity
public class Area {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "localizationId",
                referencedColumnName = "ID")
    private Localization localization;

    @ManyToOne
    @JoinColumn(name = "companyId",
                referencedColumnName = "ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "jobID",
                referencedColumnName = "ID")
    private Job job;

    public Area() {
    }

    public Area(Localization localization, Company company, Job job) {
        this.localization = localization;
        this.company = company;
        this.job = job;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Area{" +
                "localization=" + localization +
                ", company=" + company +
                ", job=" + job +
                '}';
    }
}
