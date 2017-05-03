package com.example.model.Search;

import lombok.Data;

import java.util.List;

@Data
public class SearchCompanyRequest {

    private String name;
    private String localization;
    private Float rating;
    private Integer numberJobs;
    private Integer numberOpinions;
    private Integer areaRange;
    private List<String> tags;

    public SearchCompanyRequest() {
    }

    public SearchCompanyRequest(String name, String localization, Float rating, Integer numberJobs, Integer numberOpinions, Integer areaRange, List<String> tags) {
        this.name = name;
        this.localization = localization;
        this.rating = rating;
        this.numberJobs = numberJobs;
        this.numberOpinions = numberOpinions;
        this.areaRange = areaRange;
        this.tags = tags;
    }
}
