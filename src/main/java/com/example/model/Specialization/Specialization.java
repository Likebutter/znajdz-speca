package com.example.model.Specialization;

import com.example.model.Company.Company;
import com.example.model.Job.Job;
import com.example.model.Tag.Tag;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "taggroup")
@Data
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name = "public", sequenceName = "specialization_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tagId",
                referencedColumnName = "ID")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "comapnyID",
                referencedColumnName = "ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "jobId",
                referencedColumnName = "ID")
    private Job job;

    public Specialization(){}

    public Specialization(Tag tag, Company company, Job job) {
        this.tag = tag;
        this.company = company;
        this.job = job;
    }
}
