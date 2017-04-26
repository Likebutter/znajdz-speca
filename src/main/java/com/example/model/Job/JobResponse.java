package com.example.model.Job;

import com.example.model.Client.Client;
import com.example.model.Client.ClientResponse;
import com.example.model.Company.Company;
import com.example.model.Company.CompanyResponse;
import com.example.model.Tag.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JobResponse {

    private Integer id;
    private Date beginDate;
    private Date endDate;
    private Date addedAt;
    private String localization;
    private String descript;
    private ClientResponse client;
    private CompanyResponse company;
    private List<Tag> tags;

    public JobResponse() {
    }

    public JobResponse(Integer id, Date beginDate, Date endDate, Date addedAt, String localization, String descript, Client client, Company company, List<Tag> tags) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.addedAt = addedAt;
        this.localization = localization;
        this.descript = descript;
        this.client = new ClientResponse(client);
        this.tags = tags;

        if(company != null)
            this.company = new CompanyResponse(company);
    }

    public JobResponse(Job job, List<Tag> tags) {
        this.id = job.getId();
        this.beginDate = job.getBeginDate();
        this.endDate = job.getEndDate();
        this.addedAt = job.getAddedAt();
        this.localization = job.getLocalization();
        this.descript = job.getDescript();
        this.client = new ClientResponse(job.getClient());
        this.tags = tags;

        if(job.getCompany() != null)
            this.company = new CompanyResponse((job.getCompany()));
    }
}
