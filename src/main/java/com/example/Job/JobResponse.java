package com.example.Job;


import com.example.Client.Client;
import com.example.Client.ClientResponse;
import com.example.Company.Company;
import com.example.Company.CompanyResponse;

import java.util.Date;

public class JobResponse {

    private Integer id;
    private Date beginDate;
    private Date endDate;
    private Date addedAt;
    private String localization;
    private String descript;
    private ClientResponse client;
    private CompanyResponse company;

    public JobResponse() {
    }

    public JobResponse(Integer id, Date beginDate, Date endDate, Date addedAt, String localization, String descript, Client client, Company company) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addedAt = addedAt;
        this.localization = localization;
        this.descript = descript;
        this.client = new ClientResponse(client);

        if(company != null)
            this.company = new CompanyResponse(company);
    }

    public JobResponse(Job job) {
        this.id = job.getId();
        this.beginDate = job.getBeginDate();
        this.endDate = job.getEndDate();
        this.addedAt = job.getAddedAt();
        this.localization = job.getLocalization();
        this.descript = job.getDescript();
        this.client = new ClientResponse(job.getClient());
        //this.company = new CompanyResponse((job.getCompany()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public ClientResponse getClient() {
        return client;
    }

    public void setClient(ClientResponse client) {
        this.client = client;
    }

    public CompanyResponse getCompany() {
        return company;
    }

    public void setCompany(CompanyResponse company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "JobResponse{" +
                "id=" + id +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", addedAt=" + addedAt +
                ", localization='" + localization + '\'' +
                ", descript='" + descript + '\'' +
                ", client=" + client +
                ", company=" + company +
                '}';
    }
}
