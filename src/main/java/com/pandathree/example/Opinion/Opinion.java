package com.pandathree.example.Opinion;

import com.pandathree.example.Job.Job;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
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

    public Opinion(){}

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
}
