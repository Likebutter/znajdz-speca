package com.example.Range;


import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Tag.Tag;

import javax.persistence.*;

@Entity
public class Range {

    @Id
    @GeneratedValue
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

    public Range() {
    }

    public Range(Tag tag, Company company, Job job) {
        this.tag = tag;
        this.company = company;
        this.job = job;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Range{" +
                "tag=" + tag +
                ", company=" + company +
                ", job=" + job +
                '}';
    }
}
