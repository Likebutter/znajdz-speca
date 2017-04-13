package com.example.Search;


import com.example.Tag.Tag;

import java.sql.Date;
import java.util.List;

public class SearchJobRequest {

    private Date beginDate;
    private Date endDate;
    private List<String> localizations;
    private List<String> tags;

    public SearchJobRequest() {
    }

    public SearchJobRequest(Date beginDate, Date endDate, List<String> localizations, List<String> tags) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.localizations = localizations;
        this.tags = tags;
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

    public List<String> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(List<String> localizations) {
        this.localizations = localizations;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
