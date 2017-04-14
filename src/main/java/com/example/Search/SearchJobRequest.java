package com.example.Search;


import com.example.Tag.Tag;

import java.sql.Date;
import java.util.List;

public class SearchJobRequest {

    private Date beginDate;
    private Date endDate;
    private List<String> localizations;
    private List<String> tags;
    private String origin;
    private Integer areaRange;
    //TODO: po wprowadzeniu tokenów usunąć companyId - przed tym: potrzebne do użytku testowego
    private Integer companyId;

    public SearchJobRequest() {
    }

    public SearchJobRequest(Date beginDate, Date endDate, List<String> localizations, List<String> tags, String origin, Integer areaRange, Integer companyId) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.localizations = localizations;
        this.tags = tags;
        this.origin = origin;
        this.areaRange = areaRange;
        this.companyId = companyId;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getAreaRange() {
        return areaRange;
    }

    public void setAreaRange(Integer areaRange) {
        this.areaRange = areaRange;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
