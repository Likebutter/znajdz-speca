package com.example.model.Search;


import java.sql.Date;
import java.util.List;

public class SearchJobRequest {

    private Date beginDate;
    private Date endDate;
    private String localization;
    private List<String> tags;
    private String origin;
    private Integer areaRange;
    private Integer pageNumber;

    public SearchJobRequest() {
    }

    public SearchJobRequest(Date beginDate, Date endDate, String localization, List<String> tags, String origin, Integer areaRange, Integer pageNumber) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.localization = localization;
        this.tags = tags;
        this.origin = origin;
        this.areaRange = areaRange;
        this.pageNumber = pageNumber;
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

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
