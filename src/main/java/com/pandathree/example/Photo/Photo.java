package com.pandathree.example.Photo;

import com.pandathree.example.Company.Company;
import com.pandathree.example.Job.Job;
import com.pandathree.example.Opinion.Opinion;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Photo {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "jobId",
                referencedColumnName = "ID")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "opinionId",
                referencedColumnName = "ID")
    private Opinion opinion;

    @ManyToOne
    @JoinColumn(name = "companyId",
                referencedColumnName = "ID")
    private Company company;

    @NotNull
    private String photoURL;

    public Photo(){}

    public Photo(String photoURL) {
        this.photoURL = photoURL;
    }

    public Photo(Job job, Opinion opinion, Company company, String photoURL) {
        this.job = job;
        this.opinion = opinion;
        this.company = company;
        this.photoURL = photoURL;
    }

}
