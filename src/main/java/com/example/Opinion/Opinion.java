package com.example.Opinion;

import com.example.Job.Job;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Opinion {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private Date date;

    @OneToOne(optional = false)
    @JoinColumn(name = "jobId",
                referencedColumnName = "ID")
    private Job job;

    private String text;
    private int rate;

    public Opinion() {
    }

    public Opinion(Date date, Job job) {
        this.date = date;
        this.job = job;
    }

    public Opinion(int rate, Date date, Job job, String text) {
        this.rate = rate;
        this.date = date;
        this.job = job;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "id=" + id +
                ", rate=" + rate +
                ", date=" + date +
                ", job=" + job +
                ", text='" + text + '\'' +
                '}';
    }
}
