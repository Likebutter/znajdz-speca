package com.example.Job;


import java.sql.Date;

public class JobRequest {

    private Date beginDate;
    private Date endDate;
    private String localization;
    private String descript;

    public JobRequest() {
    }

    public JobRequest(Date beginDate, Date endDate, String localization, String descript) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.localization = localization;
        this.descript = descript;
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

    @Override
    public String toString() {
        return "JobRequest{" +
                "beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", localization='" + localization + '\'' +
                ", descript='" + descript + '\'' +
                '}';
    }
}
