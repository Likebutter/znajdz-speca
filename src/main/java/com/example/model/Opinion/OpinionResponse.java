package com.example.model.Opinion;

import com.example.model.Job.Job;
import lombok.Data;

import java.util.Date;

/**
 * Created by Paweł on 2017-04-26.
 */

@Data
public class OpinionResponse {
    private int id;
    private Date date;
    private Job job;
    private String text;
    private int rate;

    public OpinionResponse(){}

    public OpinionResponse(Opinion opinion){
        this.id = opinion.getId();
        this.date = opinion.getDate();
        this.job = opinion.getJob();
        this.text = opinion.getText();
        this.rate = opinion.getRate();
    }

    public OpinionResponse(int id, Date date, Job job, String text, int rate){
        this.id = id;
        this.date = date;
        this.job = job;
        this.text = text;
        this.rate = rate;
    }
}
