package com.pandathree.example.Job;

import com.pandathree.example.Client.Client;
import com.pandathree.example.Company.Company;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Data
public class Job {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private Date beginDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Date addedAt;

    @NotNull
    private Boolean visible;

    @NotNull
    private String localization;

    @NotNull
    private Integer areaRange;

    @ManyToOne
    @JoinColumn(name = "companyId",
            referencedColumnName = "ID")
    private Company company;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clientId",
            referencedColumnName = "ID")
    private Client client;

    private String descript;

    public Job(){}

    public Job(Date beginDate, Date endDate, Date addedAt, Boolean visible, String localization, Integer areaRange, Client client) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addedAt = addedAt;
        this.visible = visible;
        this.localization = localization;
        this.areaRange = areaRange;
        this.client = client;
    }

    public Job(Date beginDate, Date endDate, Date addedAt, Boolean visible, String localization, Integer areaRange, Company company, Client client, String descript) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addedAt = addedAt;
        this.visible = visible;
        this.localization = localization;
        this.areaRange = areaRange;
        this.company = company;
        this.client = client;
        this.descript = descript;
    }

}
