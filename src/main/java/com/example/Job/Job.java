package com.example.Job;

import com.example.Client.Client;
import com.example.Company.Company;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
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

    public Job() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }



    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", addedAt=" + addedAt +
                ", visible=" + visible +
                ", company=" + company +
                ", client=" + client +
                ", descript='" + descript + '\'' +
                '}';
    }
}
